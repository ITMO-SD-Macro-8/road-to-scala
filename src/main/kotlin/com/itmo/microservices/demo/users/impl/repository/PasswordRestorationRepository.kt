package com.itmo.microservices.demo.users.impl.repository

import com.itmo.microservices.demo.users.impl.entity.PasswordVerificationModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PasswordRestorationRepository: JpaRepository<PasswordVerificationModel, UUID>