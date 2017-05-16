package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(date: MyDate) = when {
        year != date.year -> year - date.year
        month != date.month -> month - date.month
        else -> dayOfMonth - date.dayOfMonth
    }
}

operator fun MyDate.rangeTo(other: MyDate) = DateRange(this, other)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class DateRange(override val start: MyDate, override val endInclusive: MyDate) : ClosedRange<MyDate>, Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> = DateIterator(this)
    override fun contains(value: MyDate): Boolean {
        return start <= value && value <= endInclusive
    }

}

class DateIterator(val dateRange: DateRange) : Iterator<MyDate> {
    var current: MyDate = dateRange.start
    override fun next(): MyDate {
        val result = current
        current = current.addTimeIntervals(TimeInterval.DAY, 1)
        return result
    }

    override fun hasNext(): Boolean = current <= dateRange.endInclusive
}

class RepeatedTimeInterval(val timeInterval: TimeInterval, val int: Int)

operator fun TimeInterval.times(int: Int) = RepeatedTimeInterval(this, int)

operator fun MyDate.plus(timeInterval: TimeInterval) = this.addTimeIntervals(timeInterval, 1)
operator fun MyDate.plus(repeatedTimeInterval: RepeatedTimeInterval) = this.addTimeIntervals(repeatedTimeInterval.timeInterval, repeatedTimeInterval.int)