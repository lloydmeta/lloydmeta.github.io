require "benchmark"

regex_compiled = Regexp.new("qa")
regex_compiled.match("qa")

Benchmark.bmbm(10) do |x|
  x.report("1000000 times positive regex"){1000000.times do
    "someserverqa" =~ /qa/ ? "qa" : "prod"
  end}

  x.report("1000000 times negative regex"){1000000.times do
    "someserverprod" =~ /qa/ ? "qa" : "prod"
  end}

  x.report("1000000 times positive regex compiled"){1000000.times do
    regex_compiled.match("someserverqa") ? "qa" : "prod"
  end}

  x.report("1000000 times negative regex compiled"){1000000.times do
    regex_compiled.match("someserverprod") ? "qa" : "prod"
  end}

  x.report("1000000 times positive #include?"){1000000.times do
    "someserverqa".include?("qa") ? "qa" : "prod"
  end}

  x.report("1000000 times negative #include?"){1000000.times do
    "someserverqa".include?("someserverprod") ? "qa" : "prod"
  end}

end