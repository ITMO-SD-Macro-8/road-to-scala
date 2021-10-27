package com.itmo.microservices.demo.notifications.impl.entity

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class NotificationUser {
    @Id
    var username: String? = null
    // Ignoring surname because we don't need it for notifications
    var email: String? = null

    constructor()

    constructor(username: String, email: String) {
        this.username = username
        this.email = email
    }
}