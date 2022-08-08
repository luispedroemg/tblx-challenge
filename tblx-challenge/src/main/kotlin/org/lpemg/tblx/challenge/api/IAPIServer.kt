package org.lpemg.tblx.challenge.api

interface IAPIServer<T> {
    fun start(port: Int)
}