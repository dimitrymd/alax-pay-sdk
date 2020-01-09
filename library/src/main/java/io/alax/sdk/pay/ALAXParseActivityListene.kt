package io.alax.sdk.pay

import io.alax.sdk.pay.model.TransactionConfirmation

interface ALAXParseActivityListener {
  fun onParsed(blockNum:Long, trxInBlock:Long, isParsed:Boolean, transactionConfirmation:TransactionConfirmation)

}
