require 'hash_walker'

json_response = {
    "title"=> "Hilarious article",
    "subtitle"=> "Something people might find funny",
    "description"=> "super funny stuff",
    "system"=> {
        "categories"=> [
            "high ratings",
            "link bait"
        ],
        "description"=> "really not that funny"
    },
    "author"=> {
        "name"=> "Some Body",
        "body"=> "Not too bad"
    },
    "categories"=> [
        "Life",
        "Happiness",
        "Jokes"
    ],
    "content"=> {
        "categories"=> [
            "Life",
            "Happiness",
            "Jokes"
        ],
        "body"=> [
            "lots of jokes here",
            "lots of jokes here again !",
            "last of the jokes !",
        ]
    }
}

keys_to_read = [
        "title",
        "author" => ["name"],
        "categories",
        "content" => [
            "body"
        ]
    ]

json_response.each_primitive_value_at(keys_to_read){|value,path|
    puts "#{value}, #{path}"
}

# Output

# Hilarious article, ["title"]
# Some Body, ["author", "body"]
# Life, ["a_array", 0]
# Happiness, ["a_array", 1]
# Jokes, ["a_array", 2]
# lots of jokes here, ["content", body", 0]
# lots of jokes here again !, ["content", body", 1]
# last of the jokes !, ["content", body", 2]