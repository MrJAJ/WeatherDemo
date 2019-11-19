package com.example.weatherdemo.domain

public interface Command<T> {
    fun execute(): T
}