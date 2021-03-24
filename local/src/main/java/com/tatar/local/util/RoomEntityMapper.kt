package com.tatar.local.util

import io.reactivex.rxjava3.core.Flowable

interface RoomEntityMapper<Data, RoomEntity> {
    fun mapToData(roomEntity: RoomEntity): Data
    fun mapFromData(data: Data): RoomEntity
}

fun <Data, RoomEntity> Flowable<List<RoomEntity>>.toDataList(
    roomEntityMapper: RoomEntityMapper<Data, RoomEntity>
): Flowable<List<Data>> = this.map {
    it.map { roomEntity -> roomEntityMapper.mapToData(roomEntity) }
}

fun <Data, RoomEntity> Data.fromData(
    roomEntityMapper: RoomEntityMapper<Data, RoomEntity>
): RoomEntity = roomEntityMapper.mapFromData(this)
