/*
 * Copyright 2019 copyright eastar Jeong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.eastar.pref.annotation.generator

import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

public class AnnotationGenerator : AbstractProcessor() {
    override fun init(p0: ProcessingEnvironment?) {
        super.init(p0)
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return super.getSupportedSourceVersion()
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return super.getSupportedAnnotationTypes()
    }

    override fun process(p0: MutableSet<out TypeElement>?, p1: RoundEnvironment?): Boolean {
        return true
    }
}
