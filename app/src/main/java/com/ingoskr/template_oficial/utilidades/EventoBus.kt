package com.ingoskr.template_oficial.utilidades

import org.greenrobot.eventbus.EventBus


object EventoBus {
    private val bus = EventBus.getDefault()

    fun registrar(suscriptor: Any) {
        if (!bus.isRegistered(suscriptor)) {
            bus.register(suscriptor)
        }
    }

    fun desregistrar(suscriptor: Any) {
        if (bus.isRegistered(suscriptor)) {
            bus.unregister(suscriptor)
        }
    }

    fun publicar(evento: Any) {
        bus.post(evento)
    }
}