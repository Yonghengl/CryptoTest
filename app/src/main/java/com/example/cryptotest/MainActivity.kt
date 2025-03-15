package com.example.cryptotest

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.example.cryptotest.databinding.ActivityMainBinding
import com.example.cryptotest.ui.theme.CryptoTestTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

const val TAG = "CroptyTest"

class MainActivity : FragmentActivity() {
    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_main, null, false)
        setContentView(mBinding.root);
        supportFragmentManager.beginTransaction()
            .add(R.id.main_frame, WalletFrragment(), "wallet")
            .commitNow();
    }

    fun test() = runBlocking {
        launch {
            // 获取生成的 Flow
            simpleFlow().collect { value ->
                Log.e(TAG, "test value=$value")
            }
        }

        // 让主线程等待一段时间，确保 Flow 发出数据
        delay(3000) // 等待 3 秒后结束程序   }
    }

    // 创建一个简单的 Flow
    fun simpleFlow(): Flow<Int> = flow {
        for (i in 1..5) {
            delay(1000) // 模拟耗时操作
            emit(i) // 发出当前的 i 值
        }
    }
}
