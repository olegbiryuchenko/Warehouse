package com.biryuchenko.calculate

import junit.framework.TestCase.assertEquals
import org.junit.Test


class CalculatorUnitTest {

    private val calculator = Calculator()

    @Test
    fun `pricePerUnit should calculate correctly with positive values`() {
        // Test case 1: No percentage, exact division
        assertEquals(10, calculator.pricePerUnit(100.0, 10, 0))

        // Test case 2: With percentage, exact division
        assertEquals(11, calculator.pricePerUnit(100.0, 10, 10))

        // Test case 3: With percentage, needs ceiling
        assertEquals(34, calculator.pricePerUnit(100.0, 3, 0)) // 100 / 3 = 33.33 -> 34
        assertEquals(37, calculator.pricePerUnit(100.0, 3, 10)) // 110 / 3 = 36.66 -> 37

        // Test case 4: Large numbers
        assertEquals(1050, calculator.pricePerUnit(100000.0, 100, 5))
    }

    @Test
    fun `pricePerUnit should handle zero percent correctly`() {
        assertEquals(10, calculator.pricePerUnit(100.0, 10, 0))
    }

    @Test
    fun `pricePerUnit should handle zero count gracefully`() {
        // Division by zero would normally occur, but the try-catch in the original
        // code handles it by returning 0.
        assertEquals(0, calculator.pricePerUnit(100.0, 0, 10))
        assertEquals(0, calculator.pricePerUnit(0.0, 0, 10))
    }

    @Test
    fun `pricePerUnit should handle zero price correctly`() {
        assertEquals(0, calculator.pricePerUnit(0.0, 10, 10))
        assertEquals(0, calculator.pricePerUnit(0.0, 10, 0))
    }

    @Test
    fun `price should calculate correctly with positive values`() {
        assertEquals(100, calculator.price(10, 10))
        assertEquals(0, calculator.price(0, 10))
        assertEquals(0, calculator.price(10, 0))
        assertEquals(1500, calculator.price(150, 10))
    }

    @Test
    fun `price should handle large numbers`() {
        assertEquals(1000000, calculator.price(1000, 1000))
    }

    @Test
    fun `calc should return correct pair for positive values`() {
        // pricePerUnit = ceil(((100 + (100 * (10) / 100)) / 10)).toInt() = ceil((110 / 10)).toInt() = 11
        // price = 11 * 10 = 110
        assertEquals(Pair(11, 110), calculator.calc(100.0, 10, 10))

        // pricePerUnit = ceil(((100 + (100 * (0) / 100)) / 3)).toInt() = ceil((100 / 3)).toInt() = 34
        // price = 34 * 3 = 102
        assertEquals(Pair(34, 102), calculator.calc(100.0, 3, 0))
    }

    @Test
    fun `calc should handle zero count gracefully`() {
        // If count is 0, pricePerUnit returns 0, and then price(0, 0) returns 0.
        assertEquals(Pair(0, 0), calculator.calc(100.0, 0, 10))
    }

    @Test
    fun `calc should handle zero price gracefully`() {
        // If price is 0, pricePerUnit returns 0, and then price(0, count) returns 0.
        assertEquals(Pair(0, 0), calculator.calc(0.0, 10, 10))
    }

    @Test
    fun `pricePerUnit should calculate correctly for exact divisions with zero percent`() {
        // 100 кг корма по 10 за кг, без наценки
        assertEquals(10, calculator.pricePerUnit(100.0, 10, 0))
    }

    @Test
    fun `pricePerUnit should calculate correctly for exact divisions with positive percent`() {
        // 100 кг корма по 10 за кг, с 10% наценкой (110 / 10 = 11)
        assertEquals(11, calculator.pricePerUnit(100.0, 10, 10))
    }

    @Test
    fun `pricePerUnit should round up for non-exact divisions with zero percent (e_g_ bulky items)`() {
        // 100 пакетов наполнителя на 3 паллеты, без наценки (100 / 3 = 33.33 -> 34)
        assertEquals(34, calculator.pricePerUnit(100.0, 3, 0))
    }

    @Test
    fun `pricePerUnit should round up for non-exact divisions with positive percent (e_g_ fragile items)`() {
        // 100 хрупких игрушек, 10% наценка, на 3 коробки (110 / 3 = 36.66 -> 37)
        assertEquals(37, calculator.pricePerUnit(100.0, 3, 10))
    }

    @Test
    fun `pricePerUnit should handle large quantities and prices (e_g_ bulk order of feed)`() {
        // 100000 кг корма, 5% наценка, 100 поставок
        // (100000 + 5000) / 100 = 105000 / 100 = 1050
        assertEquals(1050, calculator.pricePerUnit(100000.0, 100, 5))
    }

    @Test
    fun `pricePerUnit should return 0 if count is zero (division by zero scenario)`() {
        // Пришла поставка с ценой, но количество не указано (ошибка или нулевое количество)
        assertEquals(0, calculator.pricePerUnit(100.0, 0, 10))
        // Нулевая цена и нулевое количество
        assertEquals(0, calculator.pricePerUnit(0.0, 0, 10))
    }

    @Test
    fun `pricePerUnit should return 0 if price is zero (free items or no cost)`() {
        // Бесплатные образцы или товар с нулевой стоимостью
        assertEquals(0, calculator.pricePerUnit(0.0, 10, 10))
        assertEquals(0, calculator.pricePerUnit(0.0, 10, 0))
    }


    @Test
    fun `pricePerUnit should handle negative percent (if interpreted as discount)`() {
        // Если процент может быть отрицательным (скидка).
        // Допустим, 100.0 цена, 10 шт, -10% скидка: (100 - 10) / 10 = 90 / 10 = 9
        // Обратите внимание: текущая реализация плюсует процент. Если это скидка,
        // нужно изменить формулу на `price - (price * (abs(percent)) / 100)`.
        // В рамках текущей логики: 100 + (100 * -10 / 100) = 100 - 10 = 90.
        assertEquals(9, calculator.pricePerUnit(100.0, 10, -10))
    }

    @Test
    fun `pricePerUnit should handle very small prices and counts`() {
        // Очень дешевый товар, много штук
        assertEquals(1, calculator.pricePerUnit(0.5, 10, 0)) // 0.5 / 10 = 0.05 -> ceil(0.05) = 1
        assertEquals(1, calculator.pricePerUnit(0.1, 1, 0)) // 0.1 / 1 = 0.1 -> ceil(0.1) = 1
        assertEquals(
            1,
            calculator.pricePerUnit(0.01, 1, 10)
        ) // 0.01 + 0.001 = 0.011 -> ceil(0.011) = 1
    }

    // --- Тесты для price ---

    @Test
    fun `price should calculate total cost correctly`() {
        // 10 упаковок корма по 10 за упаковку
        assertEquals(100, calculator.price(10, 10))
        // Бесплатные образцы
        assertEquals(0, calculator.price(0, 10))
        // Нет количества
        assertEquals(0, calculator.price(10, 0))
        // Более крупная поставка
        assertEquals(1500, calculator.price(150, 10))
    }

    @Test
    fun `price should handle large numbers for total cost`() {
        // Общая стоимость большой партии
        assertEquals(1000000, calculator.price(1000, 1000))
    }

    @Test
    fun `price should handle negative unit price (if applicable, e_g_ refund)`() {
        // Если pricePerUnit может вернуть отрицательное значение (например, при возврате).
        assertEquals(-50, calculator.price(-5, 10))
    }


    // --- Тесты для calc (интеграционные) ---

    @Test
    fun `calc should return correct calculated values for a typical product batch`() {
        // 100.0 - цена за партию корма, 10 - количество мешков, 10 - 10% наценка
        // pricePerUnit: (100 + 10%) / 10 = 110 / 10 = 11
        // price: 11 * 10 = 110
        assertEquals(Pair(11, 110), calculator.calc(100.0, 10, 10))
    }

    @Test
    fun `calc should handle products requiring rounding up per unit`() {
        // 100.0 - цена за партию игрушек, 3 - количество коробок, 0 - без наценки
        // pricePerUnit: 100 / 3 = 33.33 -> 34
        // price: 34 * 3 = 102
        assertEquals(Pair(34, 102), calculator.calc(100.0, 3, 0))
    }

    @Test
    fun `calc should handle zero count gracefully, resulting in zero cost`() {
        // Заказали товар, но пришло 0 штук (брак, недопоставка)
        assertEquals(Pair(0, 0), calculator.calc(100.0, 0, 10))
    }

    @Test
    fun `calc should handle zero price gracefully, indicating free or no-cost items`() {
        // Пришли бесплатные образцы корма
        assertEquals(Pair(0, 0), calculator.calc(0.0, 10, 10))
    }

    @Test
    fun `calc should handle negative percentage (e_g_ discount on incoming goods)`() {
        // Цена 100.0, 10 штук, -5% скидка (например, от поставщика)
        // pricePerUnit: (100 + (100 * -5 / 100)) / 10 = (100 - 5) / 10 = 95 / 10 = 9.5 -> 10 (ceil)
        // price: 10 * 10 = 100
        assertEquals(Pair(10, 100), calculator.calc(100.0, 10, -5))
    }

    @Test
    fun `calc should handle large scale intake with substantial percentage`() {
        // Очень крупная поставка, значительная наценка (например, импорт)
        // pricePerUnit: (500000 + 25%) / 500 = (500000 + 125000) / 500 = 625000 / 500 = 1250
        // price: 1250 * 500 = 625000
        assertEquals(Pair(1250, 625000), calculator.calc(500000.0, 500, 25))
    }

    @Test
    fun `calc should handle cases with fractional unit price that rounds up to 1`() {
        // Товар по очень низкой цене, 100 штук, 0% наценки.
        // pricePerUnit: 1.0 / 100 = 0.01 -> ceil(0.01) = 1
        // price: 1 * 100 = 100
        assertEquals(Pair(1, 100), calculator.calc(1.0, 100, 0))
    }

    @Test
    fun `pricePerUnit should handle fractional prices with no percent, rounding up correctly`() {
        // Цена за партию: 136.65, Количество: 10 штук, Наценка: 0%
        // 136.65 / 10 = 13.665 -> ceil(13.665) = 14
        assertEquals(14, calculator.pricePerUnit(136.65, 10, 0))

        // Цена за партию: 136.01, Количество: 10 штук, Наценка: 0%
        // 136.01 / 10 = 13.601 -> ceil(13.601) = 14
        assertEquals(14, calculator.pricePerUnit(136.01, 10, 0))

        // Цена за партию: 99.99, Количество: 3 штук, Наценка: 0%
        // 99.99 / 3 = 33.33 -> ceil(33.33) = 34
        assertEquals(34, calculator.pricePerUnit(99.99, 3, 0))
    }

    @Test
    fun `pricePerUnit should handle fractional prices with positive percent, rounding up correctly`() {
        // Цена за партию: 136.65, Количество: 10 штук, Наценка: 5%
        // 136.65 + (136.65 * 0.05) = 136.65 + 6.8325 = 143.4825
        // 143.4825 / 10 = 14.34825 -> ceil(14.34825) = 15
        assertEquals(15, calculator.pricePerUnit(136.65, 10, 5))

        // Цена за партию: 250.75, Количество: 7 штук, Наценка: 12%
        // 250.75 + (250.75 * 0.12) = 250.75 + 30.09 = 280.84
        // 280.84 / 7 = 40.12 -> ceil(40.12) = 41
        assertEquals(41, calculator.pricePerUnit(250.75, 7, 12))
    }

    @Test
    fun `pricePerUnit should handle fractional prices with negative percent (discount), rounding up correctly`() {
        // Цена за партию: 136.65, Количество: 10 штук, Скидка: 5%
        // 136.65 + (136.65 * -0.05) = 136.65 - 6.8325 = 129.8175
        // 129.8175 / 10 = 12.98175 -> ceil(12.98175) = 13
        assertEquals(13, calculator.pricePerUnit(136.65, 10, -5))
    }

    @Test
    fun `pricePerUnit should handle edge case where result is exactly X_0 but should still be X`() {
        // Проверка, что ceil(X.0) = X, а не X+1 из-за ошибок точности
        // 110.0 / 10 = 11.0 -> ceil(11.0) = 11
        assertEquals(11, calculator.pricePerUnit(110.0, 10, 0))

        // 135.0 / 3 = 45.0 -> ceil(45.0) = 45
        assertEquals(45, calculator.pricePerUnit(135.0, 3, 0))

        // 136.65 + (136.65 * 0.05) = 143.4825. Если бы было ровно 140, то 140/10=14.0
        // Для демонстрации:
        // (140.0 + (140.0 * 0.0)) / 10 = 14.0 -> ceil(14.0) = 14
        assertEquals(14, calculator.pricePerUnit(140.0, 10, 0))
    }

    @Test
    fun `calc should handle fractional prices and quantities with positive percent`() {
        // Цена за партию: 136.65, Количество: 10 штук, Наценка: 5%
        // pricePerUnit: (136.65 + 5%) / 10 = 14.34825 -> 15
        // price: 15 * 10 = 150
        assertEquals(Pair(15, 150), calculator.calc(136.65, 10, 5))
    }

    @Test
    fun `calc should handle fractional prices and quantities with no percent`() {
        // Цена за партию: 99.99, Количество: 3 штук, Наценка: 0%
        // pricePerUnit: 99.99 / 3 = 33.33 -> 34
        // price: 34 * 3 = 102
        assertEquals(Pair(34, 102), calculator.calc(99.99, 3, 0))
    }

    @Test
    fun `calc should correctly process hypoallergenic dog treats batch from invoice`() {
        // Invoice Item 1: Premium Hypoallergenic Dog Treats
        val price = 245.78      // Total batch price
        val count = 75          // Number of individual treat packs
        val percent = 7         // 7% import/handling fee

        // Calculation Breakdown:
        // 1. Price including percent: 245.78 + (245.78 * 0.07) = 245.78 + 17.2046 = 262.9846
        // 2. Price per unit: ceil(262.9846 / 75) = ceil(3.50646133...) = 4
        // 3. Total price from per unit: 4 * 75 = 300
        val expectedPricePerUnit = 4
        val expectedTotalPrice = 300
        assertEquals(
            Pair(expectedPricePerUnit, expectedTotalPrice),
            calculator.calc(price, count, percent)
        )
    }

    @Test
    fun `calc should correctly process specialty aquarium gravel with a discount from invoice`() {
        // Invoice Item 2: Specialty Aquarium Gravel
        val price = 189.99
        val count = 15
        val percent = -3 // -3% discount

        // Calculation Breakdown:
        // 1. Price including percent: 189.99 + (189.99 * -0.03) = 189.99 - 5.6997 = 184.2903
        // 2. Price per unit: ceil(184.2903 / 15) = ceil(12.28602) = 13
        // 3. Total price from per unit: 13 * 15 = 195
        val expectedPricePerUnit = 13
        val expectedTotalPrice = 195
        assertEquals(
            Pair(expectedPricePerUnit, expectedTotalPrice),
            calculator.calc(price, count, percent)
        )
    }

    @Test
    fun `calc should correctly process assorted small rodent cages from invoice`() {
        // Invoice Item 3: Assorted Small Rodent Cages
        val price = 412.35
        val count = 8
        val percent = 0 // No additional cost

        // Calculation Breakdown:
        // 1. Price including percent: 412.35 + (412.35 * 0.00) = 412.35
        // 2. Price per unit: ceil(412.35 / 8) = ceil(51.54375) = 52
        // 3. Total price from per unit: 52 * 8 = 416
        val expectedPricePerUnit = 52
        val expectedTotalPrice = 416
        assertEquals(
            Pair(expectedPricePerUnit, expectedTotalPrice),
            calculator.calc(price, count, percent)
        )
    }

    @Test
    fun `calc should correctly process premium dog food batch from invoice`() {
        // Invoice Item: Premium Dog Food (Large Bags)
        val price = 550.75    // Total batch price
        val count = 20        // Number of bags
        val percent = 8       // 8% markup/cost

        // Calculation Breakdown for Dog Food:
        // 1. Price including percent: 550.75 + (550.75 * 0.08) = 550.75 + 44.06 = 594.81
        // 2. Price per unit: ceil(594.81 / 20) = ceil(29.7405) = 30
        // 3. Total price from per unit: 30 * 20 = 600

        val expectedPricePerUnit = 30
        val expectedTotalPrice = 600
        assertEquals(
            Pair(expectedPricePerUnit, expectedTotalPrice),
            calculator.calc(price, count, percent)
        )
    }

    @Test
    fun `calc should correctly process cat litter bulk purchase from invoice`() {
        // Invoice Item: Cat Litter (Bulk Purchase)
        val price = 180.00
        val count = 12
        val percent = 0

        // Calculation Breakdown for Cat Litter:
        // 1. Price including percent: 180.00 + (180.00 * 0.00) = 180.00
        // 2. Price per unit: ceil(180.00 / 12) = ceil(15.0) = 15
        // 3. Total price from per unit: 15 * 12 = 180

        val expectedPricePerUnit = 15
        val expectedTotalPrice = 180
        assertEquals(
            Pair(expectedPricePerUnit, expectedTotalPrice),
            calculator.calc(price, count, percent)
        )
    }

    @Test
    fun `calc should correctly process assorted pet toys batch from invoice`() {
        // Invoice Item: Assorted Pet Toys (Small Items)
        val price = 75.20
        val count = 50
        val percent = 15 // 15% markup/cost

        // Calculation Breakdown for Pet Toys:
        // 1. Price including percent: 75.20 + (75.20 * 0.15) = 75.20 + 11.28 = 86.48
        // 2. Price per unit: ceil(86.48 / 50) = ceil(1.7296) = 2
        // 3. Total price from per unit: 2 * 50 = 100

        val expectedPricePerUnit = 2
        val expectedTotalPrice = 100
        assertEquals(
            Pair(expectedPricePerUnit, expectedTotalPrice),
            calculator.calc(price, count, percent)
        )
    }

    // You can add more tests for edge cases specific to invoice processing,
    // e.g., an item with 0 quantity (damaged/missing from delivery).
    @Test
    fun `calc should handle invoice item with zero quantity due to damage`() {
        val price = 50.0
        val count = 0 // Item ordered, but 0 received/usable
        val percent = 10

        // Expected: If count is 0, pricePerUnit returns 0, leading to a total price of 0.
        assertEquals(Pair(0, 0), calculator.calc(price, count, percent))
    }


    @Test

    fun `calc should handle whole prices and quantities with no percent`() {
        // Входные данные: priceIn=100.0, count=2, percent=0
        // pricePerUnit(100.0, 2, 0) = (100.0 + 0) / 2 = 50.0 -> ceil(50.0) = 50
        // price(50, 2) = 50 * 2 = 100
        assertEquals(Pair(50, 100), calculator.calc(100.0, 2, 0))
    }

    @Test

    fun `calc should handle fractional prices with percent`() {
        // Входные данные: priceIn=100.0, count=3, percent=10
        // pricePerUnit(100.0, 3, 10) = (100.0 + (100.0 * 10 / 100)) / 3 = (100.0 + 10.0) / 3 = 110.0 / 3 = 36.66 -> ceil(36.66) = 37
        // price(37, 3) = 37 * 3 = 111
        assertEquals(Pair(37, 111), calculator.calc(100.0, 3, 10))
    }

    @Test

    fun `calc should return zeros when count is zero`() {
        // Входные данные: priceIn=50.0, count=0, percent=5
        // pricePerUnit(50.0, 0, 5) -> возвращает 0
        // price(0, 0) -> возвращает 0
        assertEquals(Pair(0, 0), calculator.calc(50.0, 0, 5))
    }


    @Test

    fun `pricePerUnit should calculate correctly with positive values and no percent`() {
        // (100.0 + (100.0 * 0 / 100)) / 4 = 100.0 / 4 = 25.0 -> 25
        assertEquals(25, calculator.pricePerUnit(100.0, 4, 0))
    }

    @Test

    fun `pricePerUnit should calculate correctly with positive values and percent`() {
        // (100.0 + (100.0 * 20 / 100)) / 4 = (100.0 + 20.0) / 4 = 120.0 / 4 = 30.0 -> 30
        assertEquals(30, calculator.pricePerUnit(100.0, 4, 20))
    }

    @Test

    fun `pricePerUnit should handle fractional results by ceiling`() {
        // (10.0 + (10.0 * 0 / 100)) / 3 = 10.0 / 3 = 3.33... -> ceil(3.33...) = 4
        assertEquals(4, calculator.pricePerUnit(10.0, 3, 0))
    }

    @Test

    fun `pricePerUnit should return 0 when count is zero`() {
        assertEquals(0, calculator.pricePerUnit(100.0, 0, 10))
    }

    @Test

    fun `pricePerUnit should handle zero price`() {
        // (0.0 + (0.0 * 10 / 100)) / 5 = 0.0 / 5 = 0.0 -> 0
        assertEquals(0, calculator.pricePerUnit(0.0, 5, 10))
    }

    @Test

    fun `pricePerUnit should handle negative price`() {
        // (-10.0 + (-10.0 * 0 / 100)) / 3 = -10.0 / 3 = -3.33... -> ceil(-3.33...) = -3
        assertEquals(-3, calculator.pricePerUnit(-10.0, 3, 0))
    }

    @Test

    fun `price should calculate total correctly for positive values`() {
        // 50 * 2 = 100
        assertEquals(100, calculator.price(50, 2))
    }

    @Test

    fun `price should return 0 when count is zero`() {
        assertEquals(0, calculator.price(50, 0))
    }

    @Test

    fun `price should return 0 when price is zero`() {
        assertEquals(0, calculator.price(0, 5))
    }

    @Test

    fun `price should handle negative price`() {
        assertEquals(-50, calculator.price(-10, 5))
    }


    @Test

    fun `calculateTotalAmount should return correct total for positive values`() {
        // ceil(10.0 * 5) = ceil(50.0) = 50
        assertEquals(50, calculator.calculateTotalAmount(10.0, 5))
    }

    @Test

    fun `calculateTotalAmount should return 0 when count is zero`() {
        assertEquals(0, calculator.calculateTotalAmount(10.0, 0))
    }

    @Test

    fun `calculateTotalAmount should return 0 when price is zero`() {
        assertEquals(0, calculator.calculateTotalAmount(0.0, 5))
    }

    @Test

    fun `calculateTotalAmount should round up fractional results`() {
        // ceil(10.1 * 3) = ceil(30.3) = 31
        assertEquals(31, calculator.calculateTotalAmount(10.1, 3))
    }

    @Test

    fun `calculateTotalAmount should handle negative price`() {
        // ceil(-5.5 * 2) = ceil(-11.0) = -11
        assertEquals(-11, calculator.calculateTotalAmount(-5.5, 2))
    }


    @Test
    fun `calculatePricePerUnit should return correct per unit for positive values`() {
        // ceil(100.0 / 4) = ceil(25.0) = 25
        assertEquals(25, calculator.calculatePricePerUnit(100.0, 4))
    }

    @Test
    fun `calculatePricePerUnit should return 0 when count is zero`() {
        assertEquals(0, calculator.calculatePricePerUnit(100.0, 0))
    }

    @Test
    fun `calculatePricePerUnit should return 0 when price is zero`() {
        assertEquals(0, calculator.calculatePricePerUnit(0.0, 5))
    }

    @Test
    fun `calculatePricePerUnit should round up fractional results`() {
        // ceil(10.0 / 3) = ceil(3.33...) = 4
        assertEquals(4, calculator.calculatePricePerUnit(10.0, 3))
    }

    @Test
    fun `calculatePricePerUnit should handle negative price`() {
        // ceil(-10.0 / 3) = ceil(-3.33...) = -3
        assertEquals(-3, calculator.calculatePricePerUnit(-10.0, 3))
    }

    @Test
    fun `calculatePricePerUnit should handle price less than count`() {
        // ceil(7.0 / 10) = ceil(0.7) = 1
        assertEquals(1, calculator.calculatePricePerUnit(7.0, 10))
    }

}
