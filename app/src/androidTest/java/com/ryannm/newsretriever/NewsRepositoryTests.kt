package com.ryannm.newsretriever

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import com.ryannm.newsretriever.repository.NewsRepository
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NewsRepositoryTests {
    @JvmField
    @Rule
    val permissionGranter: GrantPermissionRule =
        GrantPermissionRule.grant(android.Manifest.permission.INTERNET)

    @Test
    fun returnAString() {
        runBlocking {
            NewsRepository.getNewsArticles()
        }
    }

}