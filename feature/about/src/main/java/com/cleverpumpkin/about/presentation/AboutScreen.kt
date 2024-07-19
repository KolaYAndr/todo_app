package com.cleverpumpkin.about.presentation

import android.content.Context
import android.view.ContextThemeWrapper
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.yandex.div.DivDataTag
import com.yandex.div.core.Div2Context
import com.yandex.div.core.DivConfiguration
import com.yandex.div.core.expression.variables.DivVariableController
import com.yandex.div.core.view2.Div2View
import com.yandex.div.data.DivParsingEnvironment
import com.yandex.div.data.Variable
import com.yandex.div.json.ParsingErrorLogger
import com.yandex.div.picasso.PicassoDivImageLoader
import com.yandex.div2.DivData
import com.yandex.div2.DivVariable

@Composable
fun AboutScreen(onNavigation: () -> Unit, contextThemeWrapper: ContextThemeWrapper, modifier: Modifier = Modifier) {
    Scaffold { paddingValues ->
        val contextWrapper = remember { contextThemeWrapper }
        val assetReader = remember { AssetReader() }
        AndroidView(
            factory = { context ->
                val configuration = createDivConfiguration(context, onNavigation)
                Div2View(
                    Div2Context(
                        baseContext = contextWrapper,
                        configuration = configuration,
                        lifecycleOwner = context as LifecycleOwner
                    ).also { div2Context: Div2Context ->
                        val variable = Variable.StringVariable("app_theme", "dark")
                        div2Context.divVariableController.putOrUpdate(variable)
                    }
                )
            },
            modifier = modifier.padding(paddingValues)
        ) { div2View ->
            val divJson = assetReader.read(div2View.context.assets.open("about"))
            val templatesJson = divJson.getJSONObject("templates")
            val cardJson = divJson.getJSONObject("card")

            val environment = DivParsingEnvironment(ParsingErrorLogger.ASSERT).apply {
                parseTemplates(templatesJson)
            }

            val divData = DivData(environment, cardJson)

            div2View.setData(divData, DivDataTag("div"))

        }
    }
}

private fun createDivConfiguration(context: Context, onNavigation: () -> Unit): DivConfiguration {
    val variableController = DivVariableController()
    return DivConfiguration.Builder(PicassoDivImageLoader(context))
        .divVariableController(variableController)
        .visualErrorsEnabled(true)
        .actionHandler(CustomDivActionHandler(onNavigation))
        .build()
}