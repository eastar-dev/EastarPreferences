package dev.eastar.pref.annotation.generator

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeSpec
import dev.eastar.pref.annotation.Pref
import dev.eastar.pref.annotation.generator.AnnotationConst.Companion.CLASS_TAIL
import dev.eastar.pref.annotation.generator.AnnotationConst.Companion.GENERATED_INITIALIZER_CLASS
import dev.eastar.pref.annotation.generator.AnnotationConst.Companion.PACKAGE_NAME
import dev.eastar.pref.annotation.util.Log
import javax.lang.model.element.Element

class ClassBuilderInitializer(environments: Set<Element>) {


    init {
        Log.w("Generate Initializer Class : [$GENERATED_INITIALIZER_CLASS]")

        // Build onCreate() method with preference initialization code
        val onCreateMethod: FunSpec = FunSpec.builder("onCreate")
            .addModifiers(KModifier.OVERRIDE)
            .returns(Boolean::class)
            .apply {
                environments.forEach { element ->
                    val className = element.simpleName.removeSuffix(CLASS_TAIL)
                    val ann = element.getAnnotation(Pref::class.java)

                    var packageName = element.enclosingElement.toString()
                    if (packageName == "unnamed package") {
                        packageName = "unnamed"
                    }

                    val preferenceName = when {
                        ann.defaultSharedPreferences ->
                            "androidx.preference.PreferenceManager.getDefaultSharedPreferences(context!!)"

                        ann.value.isNotBlank() ->
                            """context?.getSharedPreferences("${ann.value}", android.content.Context.MODE_PRIVATE)!!"""

                        else ->
                            """context?.getSharedPreferences("$element", android.content.Context.MODE_PRIVATE)!!"""
                    }

                    addStatement("%L.%L.preferences = %L", packageName, className, preferenceName)
                }
            }
            .addStatement("return true")
            .build()


    }

}


fun getContent() = fileSpec.toString()


// Build the file
private val fileSpec: FileSpec = FileSpec.builder(PACKAGE_NAME, GENERATED_INITIALIZER_CLASS)
    .addType(
        generateContentProviderClass {
            onCreateMethod
        }
    )
    .build()

fun generateContentProviderClass(onCreateMethodBuilder: () -> FunSpec): TypeSpec {
    // Build the ContentProvider class
    return TypeSpec.classBuilder(GENERATED_INITIALIZER_CLASS)
        .superclass(ClassName("android.content", "ContentProvider"))
        .addFunction(onCreateMethodBuilder())
        .addFunction(
            FunSpec.builder("getType")
                .addModifiers(KModifier.OVERRIDE)
                .addParameter("uri", ClassName("android.net", "Uri"))
                .returns(ClassName("kotlin", "String").copy(nullable = true))
                .addStatement("return null")
                .build()
        )
        .addFunction(
            FunSpec.builder("insert")
                .addModifiers(KModifier.OVERRIDE)
                .addParameter("uri", ClassName("android.net", "Uri"))
                .addParameter("values", ClassName("android.content", "ContentValues").copy(nullable = true))
                .returns(ClassName("android.net", "Uri").copy(nullable = true))
                .addStatement("return null")
                .build()
        )
        .addFunction(
            FunSpec.builder("query")
                .addModifiers(KModifier.OVERRIDE)
                .addParameter("uri", ClassName("android.net", "Uri"))
                .addParameter("projection", ClassName("kotlin", "Array").parameterizedBy(ClassName("kotlin", "String")).copy(nullable = true))
                .addParameter("selection", ClassName("kotlin", "String").copy(nullable = true))
                .addParameter("selectionArgs", ClassName("kotlin", "Array").parameterizedBy(ClassName("kotlin", "String")).copy(nullable = true))
                .addParameter("sortOrder", ClassName("kotlin", "String").copy(nullable = true))
                .returns(ClassName("android.database", "Cursor").copy(nullable = true))
                .addStatement("return null")
                .build()
        )
        .addFunction(
            FunSpec.builder("update")
                .addModifiers(KModifier.OVERRIDE)
                .addParameter("uri", ClassName("android.net", "Uri"))
                .addParameter("values", ClassName("android.content", "ContentValues").copy(nullable = true))
                .addParameter("selection", ClassName("kotlin", "String").copy(nullable = true))
                .addParameter("selectionArgs", ClassName("kotlin", "Array").parameterizedBy(ClassName("kotlin", "String")).copy(nullable = true))
                .returns(ClassName("kotlin", "Int"))
                .addStatement("return 0")
                .build()
        )
        .addFunction(
            FunSpec.builder("delete")
                .addModifiers(KModifier.OVERRIDE)
                .addParameter("uri", ClassName("android.net", "Uri"))
                .addParameter("selection", ClassName("kotlin", "String").copy(nullable = true))
                .addParameter("selectionArgs", ClassName("kotlin", "Array").parameterizedBy(ClassName("kotlin", "String")).copy(nullable = true))
                .returns(ClassName("kotlin", "Int"))
                .addStatement("return 0")
                .build()
        )
        .build()
}