val createAndModifyObserver = Observer[EventAtPath](onNext = { event => println(s"Something was created or modified: $event")})
val createOnlyObserver = Observer[EventAtPath](onNext = { event => println(s"Something was created: $event")})

observable.subscribe(createAndModifyObserver)
createsOnly.subscribe(createOnlyObserver)
/*
 * The same as
 * {{{
 * val createScalaOnlyObserver = Observer[EventAtPath](onNext = { event => println(s"A Scala source file was created: $event")})
 * scalaSourceCreatesOnly.subscribe(createScalaOnlyObserver)
 * }}}
 *
 * The same as declaring an Observer separately and attaching it via #subscribe (as seen above),
 * since Observer as a type is just a way of binding 3 different functions, onNext, onCompleted, and onError
 *
 */
scalaSourceCreatesOnly.subscribe(onNext = { event => println(s"A Scala source file was created: $event")})
