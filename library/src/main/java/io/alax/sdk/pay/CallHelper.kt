package io.alax.sdk.pay

import android.app.Activity
import io.alax.sdk.pay.AlaxPay
import io.alax.sdk.pay.model.ProcessedTransaction
import io.alax.sdk.pay.model.TransactionConfirmation
import io.alax.sdk.pay.model.TransferInput

object CallHelper {
  /**
   * Initializer
   */
  @JvmStatic
  @JvmOverloads
  fun init() = AlaxPay.init()

  /**
   * Starts a transfer activity
   */
  @JvmStatic
  @JvmOverloads
  fun requestTransferActivity(input: TransferInput, activity: Activity) {
    if (!AlaxPay.IsInit)
      AlaxPay.init()

    if (activity != null)
      AlaxPay.Ui.requestTransferActivity(input, activity)
  }

  /**
   * Builder for a TransferInput Object
   */
  @JvmStatic
  @JvmOverloads
  fun buildTransferInput(
    receiver: String,
    amountInput: Float,
    assetCode: String,
    xApiKey: String
  ): TransferInput {
    val amount = amountInput.toBigDecimal()
    return TransferInput.build(receiver, amount, assetCode, xApiKey)
  }

  /**
   * Transfer Verification
   */
  @JvmStatic
  @JvmOverloads
  fun verifyTransfer(transactionConfirmation: TransactionConfirmation) : ProcessedTransaction
  {
    if (!AlaxPay.IsInit)
      AlaxPay.init()
    return AlaxPay.Api.syncVerifyTransfer(transactionConfirmation)
  }

  /**
   * Transfer Verification with primitive parameters
   */
  fun verifyTransfer2(_blockNum: Long, _trxInBlock: Long): ProcessedTransaction
  {
    if (!AlaxPay.IsInit)
      AlaxPay.init()
    val transactionConfirmation = TransactionConfirmation(blockNum = _blockNum, trxInBlock = _trxInBlock)
    return AlaxPay.Api.syncVerifyTransfer(transactionConfirmation)
  }

  @JvmStatic
  @JvmOverloads
  fun setParseResultListener(listener: ALAXParseActivityListener)
  {
    ALAXParseResponseActivity.listener = listener
  }

  private var _currentActivity: Activity? = null
  private var _latestTransactionConfirmation: TransactionConfirmation? = null
  private var _isConfirmed: Boolean = false

  @JvmStatic
  @JvmOverloads
  fun getCurrentActivity():Activity? = _currentActivity

  @JvmStatic
  @JvmOverloads
  fun setCurrentActivity(activity: Activity) {
    _currentActivity = activity
  }

  @JvmStatic
  @JvmOverloads
  fun getLatestTransactionConfrimation():TransactionConfirmation? = _latestTransactionConfirmation

  @JvmStatic
  @JvmOverloads
  fun setLastTransactionConfrimation(transactionConfirmation: TransactionConfirmation) {
    _latestTransactionConfirmation = transactionConfirmation
    if (transactionConfirmation.blockNum != 0L) {
      _isConfirmed = true
    }
  }

  @JvmStatic
  @JvmOverloads
  fun getIsConfrimed():Boolean? = _isConfirmed


  @JvmStatic
  @JvmOverloads
  fun resetLastTransactionData() {
    _latestTransactionConfirmation = null
    _isConfirmed = false
  }
  
}
