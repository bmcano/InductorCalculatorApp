package com.brandoncano.inductancecalculator.ui.screens.about

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FileOpen
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.brandoncano.inductancecalculator.R
import com.brandoncano.inductancecalculator.ui.composables.AppComponentPreviews
import com.brandoncano.inductancecalculator.ui.composables.AppDivider
import com.brandoncano.inductancecalculator.ui.composables.AppStandardCard
import com.brandoncano.inductancecalculator.ui.composables.ArrowButtonCard
import com.brandoncano.inductancecalculator.ui.theme.InductanceCalculatorTheme
import com.brandoncano.inductancecalculator.ui.theme.textStyleBody
import com.brandoncano.inductancecalculator.ui.theme.textStyleHeadline
import com.brandoncano.inductancecalculator.util.OpenLink

@Composable
fun AuthorCard() {
    AppStandardCard {
        HeadlineBodyStack(
            label = R.string.about_created_by,
            body = R.string.about_author,
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun AppInfoCard() {
    AppStandardCard {
        HeadlineBodyStack(
            label = R.string.about_app_version,
            body = R.string.version,
        )
        AppDivider(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp))
        HeadlineBodyStack(
            label = R.string.about_last_updated_on,
            body = R.string.last_updated,
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun ViewPrivacyPolicy(context: Context) {
    ArrowButtonCard(
        Icons.Outlined.FileOpen,
        stringResource(id = R.string.about_view_privacy_policy),
    ) {
        OpenLink.openPrivacyPolicy(context)
    }
}

@Composable
fun DescriptionCard() {
    Text(
        text = stringResource(id = R.string.about_description),
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp),
        style = textStyleHeadline(),
    )
    AppStandardCard {
        Text(
            text = stringResource(id = R.string.about_description_01),
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 12.dp),
            style = textStyleBody(),
        )
    }
}

@Composable
private fun HeadlineBodyStack(@StringRes label: Int, @StringRes body: Int) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(id = label),
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 12.dp),
            style = textStyleHeadline(),
        )
        Text(
            text = stringResource(id = body),
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 4.dp),
            style = textStyleBody(),
        )
    }
}

@AppComponentPreviews
@Composable
private fun HeadlineBodyStackPreview() {
    InductanceCalculatorTheme {
        Column(
            modifier = Modifier.height(64.dp)
        ) {
            HeadlineBodyStack(
                label = R.string.about_created_by,
                body = R.string.about_author,
            )
        }
    }
}
