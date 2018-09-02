package peter.staranchuk.githubclient.extentions


fun <T> mergeElementwise(firstArray: List<T>, secondArray: List<T>): List<T> {
    val result = arrayListOf<T>()

    val biggerArray: List<T>
    val smallerArray: List<T>

    if (firstArray.size > secondArray.size) {
        biggerArray = firstArray
        smallerArray = secondArray
    } else {
        biggerArray = secondArray
        smallerArray = firstArray
    }

    for (i in 0 until biggerArray.size) {
        result.add(biggerArray[i])

        if (i < smallerArray.size) {
            result.add(smallerArray[i])
        }
    }
    return result
}
