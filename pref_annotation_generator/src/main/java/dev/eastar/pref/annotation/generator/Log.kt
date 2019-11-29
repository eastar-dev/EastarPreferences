package dev.eastar.pref.annotation.generator

import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.Element
import javax.tools.Diagnostic

object Log {
    lateinit var processingEnvironment: ProcessingEnvironment

    //fun o(mag: CharSequence) = processingEnvironment.messager.printMessage(Diagnostic.Kind.OTHER, mag)
    //fun n(mag: CharSequence) = processingEnvironment.messager.printMessage(Diagnostic.Kind.NOTE, mag)
    //fun m(mag: CharSequence) = processingEnvironment.messager.printMessage(Diagnostic.Kind.MANDATORY_WARNING, mag)
    fun w(mag: CharSequence) = processingEnvironment.messager.printMessage(Diagnostic.Kind.WARNING, mag)
    //fun e(mag: CharSequence) = processingEnvironment.messager.printMessage(Diagnostic.Kind.ERROR, mag)

    fun environmentTree(environment: Element, depth: Int = 0) {
        w("  ".repeat(depth) + environment.simpleName)
        environment.enclosedElements.forEach { environmentTree(it, depth + 1) }
    }
}