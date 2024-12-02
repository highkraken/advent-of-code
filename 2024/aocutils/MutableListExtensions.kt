package aocutils

fun <T> MutableList<T>.swap(ind1: Int, ind2: Int)  {
    if (ind1 in this.indices && ind2 in this.indices)   {
        val temp = this[ind1]
        this[ind1] = this[ind2]
        this[ind2] = temp
    }   else    {
        throw Exception("Index out of bound!")
    }
}

fun <T> List<MutableList<T>>.inBounds(x: Int, y: Int): Boolean =
    x in this[0].indices && y in this.indices