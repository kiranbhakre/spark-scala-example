package com.sparkbyexamples.spark.rdd

import org.apache.spark.sql.SparkSession


object CreateRddMy extends App with BaseResource {
  val spark: SparkSession = SparkSession.builder
    .master("local[3]")
    .appName("CreateRddMy")
    .config("spark.eventLog.dir", "file:///Users/admin/data/spark/spark-logs")
    .config("spark.eventLog.enabled", "true")
    .getOrCreate()

  val rdd = spark.sparkContext.parallelize(Seq(("Java", 20000), ("Python", 100000), ("Scala", 3000)))
  rdd.foreach(println)

  val rdd1 = spark.sparkContext.textFile(resourceDirectory + "test.txt", 6)
  println("Num of Partitions: " + rdd1.getNumPartitions)
  val rdd11 = rdd1.repartition(4)
  println("Num of Partitions: " + rdd11.getNumPartitions)
  rdd11.foreach(println)


  val rdd2 = spark.sparkContext.wholeTextFiles(resourceDirectory + "simple*")
  rdd2.foreach(record => println("FileName : " + record._1 + ", FileContents :" + record._2))

  val rdd3 = rdd.map(row => {
    (row._1, row._2 + 100)
  })
  rdd3.foreach(println)

  val myRdd2 = spark.range(20).toDF().rdd
  myRdd2.foreach(println)
}
