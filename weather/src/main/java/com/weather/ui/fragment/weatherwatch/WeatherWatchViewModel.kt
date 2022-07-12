package com.weather.ui.fragment.weatherwatch

import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import com.weather.data.network.WeatherClient
import com.weather.model.WeatherCompleteModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.AsyncSubject

class WeatherWatchViewModel(service: WeatherClient, location: Location) :
    ViewModel() {
    val disposable = CompositeDisposable()
    val source: AsyncSubject<WeatherCompleteModel> =
        AsyncSubject.create<WeatherCompleteModel?>().apply {
            subscribeOn(Schedulers.io())
            observeOn(AndroidSchedulers.mainThread())
            doOnSubscribe {
                disposable.add(it)
            }
        }

    init {
        service.getWeather(location.latitude.toFloat(), location.longitude.toFloat())
            .doOnSubscribe { disposable.add(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result, _ ->
                source.onNext(result)
                source.onComplete()
            }
    }
}