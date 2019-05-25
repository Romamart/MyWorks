import org.apache.spark.{SparkConf, SparkContext}

object WordCount {
  def main(args: Array[String]): Unit =
  {
    System.setProperty("hadoop.home.dir", "C:\\winutils\\")

    val conf = new SparkConf().setAppName("Test").setMaster("local")
    val sc = new SparkContext(conf)

    val first_text = sc.textFile("C:\\Users\\Roman\\Desktop\\Spark_WordCount3\\texts\\Eminem.txt")
    val second_text = sc.textFile("C:\\Users\\Roman\\Desktop\\Spark_WordCount3\\texts\\Snoop.txt")

    val regex = """[^a-zA-Z ]""".r

    val words_from_first_text = first_text.flatMap(line=>regex.replaceAllIn(line, "").toLowerCase().split(" ")).collect()
    val words_from_second_text = second_text.flatMap(line=>regex.replaceAllIn(line, "").toLowerCase().split(" "))

    val first_text_all_words_count = words_from_second_text.map(word=>(word,1)).reduceByKey(_+_).collect()


    val words_second_text_from_first_text = second_text.flatMap(line=>regex.replaceAllIn(line, "").toLowerCase().split(" "))
      .filter(word=>(words_from_first_text.contains(word))).map(word=>(word,1)).reduceByKey(_+_).collect()

    println("WordCount second_text:\n")
    first_text_all_words_count.foreach(println)
    print('\n')
    println("WordCount second_text that were found in first_text:\n")
    print('\n')
    words_second_text_from_first_text.foreach(println)
  }

}
