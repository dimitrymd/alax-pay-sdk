package io.alax.sdk.pay

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import io.alax.sdk.pay.model.TransactionConfirmation
import android.util.Log

class ALAXParseResponseActivity : Activity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    CallHelper.setCurrentActivity(this)
    super.onCreate(savedInstanceState)

    Log.d("ALAX", "ParserActivityCreated()")
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    var blockNum:Long = 0
    var trxInBlock:Long = 0
    var isParsed:Boolean = false
    var transactionConfirmation:TransactionConfirmation? = null

    if (resultCode == Activity.RESULT_CANCELED)
      finish()

    if (resultCode == Activity.RESULT_OK && data != null) {
        try {
          val result = AlaxPay.Ui.parseActivityResult(data)
          Log.d("ALAX", "Result: " + result.blockNum.toString())
          blockNum = result.blockNum
          trxInBlock = result.trxInBlock
          transactionConfirmation = TransactionConfirmation(result.blockNum, result.trxInBlock)
          isParsed = true
        } catch (exception:Exception) {
          exception.printStackTrace()
        }

        if (listener != null && transactionConfirmation != null) {
          CallHelper.setLastTransactionConfrimation(transactionConfirmation)
          (listener as ALAXParseActivityListener).onParsed(
            blockNum,
            trxInBlock,
            isParsed,
            transactionConfirmation as TransactionConfirmation
          )
        }

        finish()
    }

    finish()
  }

  companion object {
    var listener:ALAXParseActivityListener? = null
  }





}
