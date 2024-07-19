package com.cleverpumpkin.about.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.yandex.div.core.DivActionHandler
import com.yandex.div.core.DivViewFacade
import com.yandex.div.json.expressions.ExpressionResolver
import com.yandex.div2.DivAction

class CustomDivActionHandler : DivActionHandler() {
    override fun handleAction(
        action: DivAction,
        view: DivViewFacade,
        resolver: ExpressionResolver
    ): Boolean {
        val url =
            action.url?.evaluate(resolver) ?: return super.handleAction(action, view, resolver)

        return if (url.scheme == SCHEME_SAMPLE && handleCustomAction(url, view.view.context)) {
            true
        } else {
            super.handleAction(action, view, resolver)
        }
    }

    private fun handleCustomAction(action: Uri, context: Context): Boolean {
        return when (action.host) {
            "link" -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(action.query))
                context.startActivity(intent)
                true
            }

            "navigation" -> {
                Toast.makeText(context, "Тут навигация", Toast.LENGTH_SHORT).show()
                true
            }

            else -> false
        }
    }

    companion object {
        const val SCHEME_SAMPLE = "custom-action"
    }
}