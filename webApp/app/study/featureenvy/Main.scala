package study.featureenvy

class Main {
  def main(args: Array[String]): Unit = {
    val address = Address(1L, 1L, 100L)
    val price = Price(10000)
    val house = House(address, price)
    val salesPerson = SalesPerson(1, "kin", house)

//    salesPerson.calculatePrice
    house.calculatePrice
    house.calculateDistance

  }

  case class House(address: Address, price: Price) {
    def calculatePrice: Int = {
      (price.number * 1.1).toInt
    }

    def calculateDistance: Long = {
      address.longitude
    }
  }

  case class SalesPerson(id: Int, name: String, house: House)

  case class Price(number: Int)

  case class Address(longitude: Long, latitude: Long, price: Long)

}
