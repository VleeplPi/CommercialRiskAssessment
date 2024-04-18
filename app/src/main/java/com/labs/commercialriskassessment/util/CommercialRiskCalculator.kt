package com.labs.commercialriskassessment.util

import android.util.Log
import kotlin.math.pow
import kotlin.math.roundToInt

class CommercialRiskCalculator(
    private val p: Double, // сумма кредита
    private val n: Double, // срок кредита
    private val i: Double, // процентная ставка по кредиту
    private val k1: Double, // курс доллара на момент начала операции
    private val k2: Double, // курс доллара на момент окнчания операции
    private val Npq: Double, // количество испорченного товара
    private val Pq: Double, // стоимость товара хорошего качества
    private val iq: Double, // скидка на испорченный товар
    private val m: Int, // количество товаров с изменением цены
    private val np: Int, // количество групп отваров с изменением цены
    private val delta: Double, // разница в изменении цены
    private val prepayment: Double, // размер предоплаты
    private val t: Double, // время до фактического получения товара
    private val dt: Double, // время фактического получения товара
    private val beta: Double // коэффициент роста капитала
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

    fun riskPoolQuality():Double{
//        Npq:Double, Pq:Double, iq:Double
        /**
         * риск товара плохого качества
         * Npq - количество испорченного товара
         * Pq - стоимость товара хорошего качества
         * iq - скидка на испорченный товар
         * **/
        Log.d(TAG, "riskPoolQuality")

        val Ppq: Double = Pq - (Pq*iq) // стоимость испорченного товара с учетом скидки на него
        return Npq *(Ppq - Pq)
    }

    fun riskIncreasePurchasePrice():Double{
//        m:Int, n:Int, delta:Double
        Log.d(TAG, "riskIncreasePurchasePrice")
        /**
         * риск увеличения закупочной цены
         * m - количество товаров с изменением цены
         * n - количество групп товаров с изменением цены
         * delta - величина изменения закупочной цены
         * **/
        var price: Double = 0.0
        for(i in 0 until m ){
            for(j in  1 .. np){
                price += delta *j
            }
        }

        return price
    }

    fun prepaymentRisk(): Double{
//        prepayment: Double, t:Double, dt: Double, beta:Double
        /**
         * риск, определяемый предоплатой
         * prepayment - размер предоплаты
         * t - время до фактического получения товара
         * dt - время фактического получения товара
         * **/
        Log.d(TAG, "prepaymentRisk")

        val pow: Double = (1+beta).pow(t/dt)

        return prepayment * pow

    }



    fun calculateRisk(): List<String>{
        /**
         * Вычисление коммерческого риска
         * **/
        Log.d(TAG, "sumCreditStartOperationWithInflation")
        val commonSummCredit: Double = simpleDeposit()
        val commonSummCreditRub = commonSummCredit * k1 // S0
        val sumOfPercentsForCredit = sumPercents(p, commonSummCreditRub)
        val purchaseAmountDollarStart = k1 * p // сумма закупки товаров и кредита в доллларах
        val purchaseAmountDollarEnd = k2 * p // сумма долга для возврата по кредиту(Pk)
        val commonSumCreditEnd = commonSummCredit *k2 // Sk
        val sumOfPercentsForCreditEnd = sumPercents(purchaseAmountDollarEnd, commonSumCreditEnd) // Ik
        val valueCurrencyRisk = currencyRisk(purchaseAmountDollarEnd, sumOfPercentsForCreditEnd)
        val inflationLvl = numberUtil.roundTo(lvlInflation(commonSumCreditEnd, commonSummCreditRub)) // a
        val inflationIndex = indexInflation(inflationLvl,1)
        val inlfationRisk =  riskInflation(commonSumCreditEnd, inflationIndex) //Ra
        val inflationRiskDollars = numberUtil.roundTo(inlfationRisk/k2)
        val ia = fisherDeposit(i, inflationLvl)
        val factDeposit = depositWithInflation(ia, inflationLvl) // ставка с учетом инфляции i
        val creditSumStartOperationWithInflation = sumCreditStartOperationWithInflation(commonSumCreditEnd, 1+inflationLvl) // S^a_k
        val factPercentsCredit = sumPercents(purchaseAmountDollarStart, creditSumStartOperationWithInflation)
        val poolQualityRisk = riskPoolQuality()
        val increasePurchasePrice = riskIncreasePurchasePrice()
        val riskPrepayment = prepaymentRisk()

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
                "Фактические проценты за кредит: ${numberUtil.roundTo(factPercentsCredit)} руб.&" +
                "Риск потери качества: ${poolQualityRisk} долл.&" +
                "Риск, связанный с увеличением закупочной цены: ${numberUtil.roundTo(increasePurchasePrice)} долл.&" +
                "Риск, определяемый предоплатой: ${numberUtil.roundTo(riskPrepayment)} руб."
        return result.split("&")
    }

}

