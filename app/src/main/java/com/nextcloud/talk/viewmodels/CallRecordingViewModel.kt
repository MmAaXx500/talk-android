/*
 * Nextcloud Talk application
 *
 * @author Marcel Hibbe
 * Copyright (C) 2022 Marcel Hibbe <dev@mhibbe.de>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.nextcloud.talk.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nextcloud.talk.models.domain.StartCallRecordingModel
import com.nextcloud.talk.models.domain.StopCallRecordingModel
import com.nextcloud.talk.repositories.callrecording.CallRecordingRepository
import com.nextcloud.talk.users.UserManager
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CallRecordingViewModel @Inject constructor(private val repository: CallRecordingRepository) : ViewModel() {

    @Inject
    lateinit var userManager: UserManager

    lateinit var roomToken: String

    sealed interface ViewState
    object RecordingStartedState : ViewState
    object RecordingStoppedState : ViewState
    object RecordingStartLoadingState : ViewState
    object RecordingStopLoadingState : ViewState
    object RecordingConfirmStopState : ViewState

    private val _viewState: MutableLiveData<ViewState> = MutableLiveData(RecordingStoppedState)
    val viewState: LiveData<ViewState>
        get() = _viewState

    private var disposable: Disposable? = null

    fun clickRecordButton() {
        if (viewState.value == RecordingStartedState) {
            _viewState.value = RecordingConfirmStopState
        } else if (viewState.value == RecordingStoppedState) {
            _viewState.value = RecordingStartLoadingState
            repository.startRecording(roomToken)
                .subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(CallStartRecordingObserver())
        }
    }

    fun stopRecording() {
        _viewState.value = RecordingStopLoadingState
        repository.stopRecording(roomToken)
            .subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(CallStopRecordingObserver())
    }

    fun dismissStopRecording() {
        _viewState.value = RecordingStartedState
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }

    fun setData(roomToken: String) {
        this.roomToken = roomToken
    }

    inner class CallStartRecordingObserver : Observer<StartCallRecordingModel> {
        override fun onSubscribe(d: Disposable) {
            // unused atm
        }

        override fun onNext(startCallRecordingModel: StartCallRecordingModel) {
            if (startCallRecordingModel.success) {
                _viewState.value = RecordingStartedState
            }
        }

        override fun onError(e: Throwable) {
            Log.e(TAG, "failure in CallStartRecordingObserver", e)
        }

        override fun onComplete() {
            // dismiss()
        }
    }

    inner class CallStopRecordingObserver : Observer<StopCallRecordingModel> {
        override fun onSubscribe(d: Disposable) {
            // unused atm
        }

        override fun onNext(stopCallRecordingModel: StopCallRecordingModel) {
            _viewState.value = RecordingStoppedState
        }

        override fun onError(e: Throwable) {
            Log.e(TAG, "failure in CallStopRecordingObserver", e)
        }

        override fun onComplete() {
            // dismiss()
        }
    }

    companion object {
        private val TAG = CallRecordingViewModel::class.java.simpleName
    }
}
