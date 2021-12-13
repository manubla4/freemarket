package com.manubla.freemarket.view.alias

import com.manubla.freemarket.data.model.base.Model
import com.manubla.freemarket.view.adapter.paging.DiffModels
import com.manubla.freemarket.view.adapter.paging.PagingModelAdapter
import com.manubla.freemarket.view.provider.ViewHolderModelProvider

internal typealias ViewHolderProvider = ViewHolderModelProvider<Model>
internal typealias PagingAdapter = PagingModelAdapter<Model>
internal typealias DiffUtil = DiffModels<Model>
