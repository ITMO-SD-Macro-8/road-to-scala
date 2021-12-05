package com.itmo.microservices.demo.items.impl.logging

import com.itmo.microservices.commonlib.logging.NotableEvent

object CatalogItemServiceNotableEvent: NotableEvent {

    private val tag: String by lazy { CatalogItemServiceNotableEvent::class.java.simpleName }

    override fun getName(): String = tag

    override fun getTemplate(): String = "Added new catalog item into catalog {}"
}