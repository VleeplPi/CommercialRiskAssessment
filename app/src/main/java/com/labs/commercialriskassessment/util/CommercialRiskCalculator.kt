package com.labs.commercialriskassessment.util

import android.util.Log
import kotlin.math.pow
import kotlin.math.roundToInt

class CommercialRiskCalculator(
    private val p: Double, // сумма кредита
    private val n: Double, // срок кредита
    private val i: Double, // процентная ставка по кредиту
    private val k1: Double, // курс доллара на момент начала операции
    private val k2: Double // курс доллара на момент окнчания операции
    ){


    private val TAG: String = this.javaClass.simpleName
    private val numberUtil: NumberUtil = NumberUtil(2);

    fun simpleDeposit(): Double{
        /**сумма выплаты кредита по простой ставке**/
        Log.d(TAG, "SIMPLE DEPOSIT")

        return p*(1+n*i)
    }

    fun sumPercents(P:Double,S: Double): Double{
        /**сумма процентов за пользование кредитом**/
        Log.d(TAG, "summPercents")
        return S - P
    }

    fun currencyRisk(Pk: Double,Ik: Double): Double{
        /**валютный риск по кредиту**/
        Log.d(TAG, "summPercents")

        val firstMultiplier: Double = Pk+Ik
        val secondMultiplier: Double = (k1-k2)/(k1*k2)
        return firstMultiplier * secondMultiplier
    }

    fun lvlInflation(Sk:Double,S0:Double): Double{
        /**уровень инфляции**/
        Log.d(TAG, "lvlInflation")
        return (Sk-S0)/S0
    }

    fun indexInflation(a:Double,periods:Int = 1):Double{
        /**индекс инфляции**/
        Log.d(TAG, "indexInflation")
        var Ii: Double = 1.0
        for(i in 0 until periods){
            Ii *= (1+a).pow(-1)
        }
        return Ii
    }

    fun riskInflation(Sk:Double,Ii:Double): Double{
        /**инфляционый риск**/
        Log.d(TAG, "riskInflation")
        return Sk*(Ii-1)
    }

    fun fisherDeposit(ia:Double,a: Double): Double{
        /**ставка фишера**/
        Log.d(TAG, "isherDeposit")
        return i + a + (i*a)
    }

    fun depositWithInflation(ia:Double,a: Double): Double{
        /**фактическая ставка(ставка по кредиту с учетом инфляции)**/
        Log.d(TAG, "depositWithInflation")
        return (ia-a)/(1+a)
    }

    fun sumCreditStartOperationWithInflation(Sk:Double, Ii:Double): Double{
        /**сумма оплаты кредита с учетом инфляции**/
        Log.d(TAG, "sumCreditStartOperationWithInflation")
        return (Sk/Ii)
    }

    fun calculateRisk(): List<String>{
        var commonSummCredit: Double = simpleDeposit()
        var commonSummCreditRub = commonSummCredit * k1 // S0
        var sumOfPercentsForCredit = sumPercents(p, commonSummCreditRub)
        var purchaseAmountDollarStart = k1 * p // сумма закупки товаров и кредита в доллларах
        var purchaseAmountDollarEnd = k2 * p // сумма долга для возврата по кредиту(Pk)
        var commonSumCreditEnd = commonSummCredit *k2 // Sk
        var sumOfPercentsForCreditEnd = sumPercents(purchaseAmountDollarEnd, commonSumCreditEnd) // Ik
        var valueCurrencyRisk = currencyRisk(purchaseAmountDollarEnd, sumOfPercentsForCreditEnd)
        var inflationLvl = numberUtil.roundTo(lvlInflation(commonSumCreditEnd, commonSummCreditRub)) // a
        var inflationIndex = indexInflation(inflationLvl,1)
        var inlfationRisk =  riskInflation(commonSumCreditEnd, inflationIndex) //Ra
        var inflationRiskDollars = numberUtil.roundTo(inlfationRisk/k2)
        var ia = fisherDeposit(i, inflationLvl)
        var factDeposit = depositWithInflation(ia, inflationLvl) // ставка с учетом инфляции i
        var creditSumStartOperationWithInflation = sumCreditStartOperationWithInflation(commonSumCreditEnd, 1+inflationLvl) // S^a_k
        var factPercentsCredit = sumPercents(purchaseAmountDollarStart, creditSumStartOperationWithInflation)
        // TODO: Виды рисков
        val result: String = "Общая сумма выплат кредита и процентов в конце операции: ${numberUtil.roundTo(commonSummCredit)} долл&\n" +
                "Обшая сумма выплат кредита и процентов в конце операции: ${numberUtil.roundTo(commonSummCreditRub)} руб&\n" +
                "Сумма процентов за пользование кредитом I: ${numberUtil.roundTo(sumOfPercentsForCredit)} долл&\n" +
                "Сумма закупки товара в рублях на начало операции: ${numberUtil.roundTo(purchaseAmountDollarStart)} руб&\n" +
                "Сумма долга по кредиту в рублях для возврата(без процентов): ${numberUtil.roundTo(purchaseAmountDollarEnd)} руб&\n" +
                "Сумма по кредиту в рублях для возврата(с процентами): ${numberUtil.roundTo(commonSumCreditEnd)} руб&\n" +
                "Сумма на оплату процентов в рублях: ${numberUtil.roundTo(sumOfPercentsForCreditEnd)} руб&\n" +
                "Величина валютного риска по кредиту составит: ${numberUtil.roundTo(valueCurrencyRisk)} долл или ${numberUtil.roundTo(valueCurrencyRisk) *k2} руб.&\n" +
                "Уровень инфляции за период кредитования: ${inflationLvl*100}%&\n" +
                "Инфляционный риск: ${numberUtil.roundTo(inlfationRisk)} руб.&\n" +
                "Инфляционный риск на конец операции по доллару США: ${inflationRiskDollars} долл.&\n" +
                "Ставка по формуле фишера: ${ia}&\n" +
                "Фактическая ставка: ${numberUtil.roundTo(factDeposit)}&\n" +
                "Вся сумма оплаты по кредиту с учетом инфляции: ${numberUtil.roundTo(creditSumStartOperationWithInflation)} руб.&\n" +
                "Фактические проценты за кредит: ${numberUtil.roundTo(factPercentsCredit)} руб."
        return result.split("&")
    }

}

