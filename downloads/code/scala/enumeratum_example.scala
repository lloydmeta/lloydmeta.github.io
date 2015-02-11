// For use in a REPL

import enumeratum._

sealed trait Phone {
  def call(number: Int): String
}

case object Phone extends Enum[Phone] {

  case object Android extends Phone {
    def call(number: Int) = "This is Larry Page."
  }

  case object Iphone extends Phone {
    def call(number: Int) = "This is Steve Jobs."
  }

  case object WindowsPhone extends Phone {
    def call(number: Int) = "This is Bill Gates."
  }

  val values = findValues

}

import Phone._

// Use as needed.

val myPhone = Iphone

// Get exhaustive match warnings
def rate(phone: Phone): String = phone match {
  case Android => "Great!"
  case Iphone => "Awesome!"
}

/*
<console>:17: warning: match may not be exhaustive.
It would fail on the following input: WindowsPhone
*/
