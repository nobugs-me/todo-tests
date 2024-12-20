Негативные кейсы 

1. Запросить требования для каждого поля из JSON

TODO 
0 < id < 1000000
text, от 1 до 200 символов, запрещенные символы #$%
completed, либо false, либо true 

2. Формируем тест кейсы на основании требований, следуя техникам тест дизайна

#id 
User cannot create todo with negative id (-1)
User cannot create todo with id more than 1.000.000 (1000001)

#text 
User cannot create todo without text ("")
User cannot create todo with text with exceed length (201 symbol)
User cannot create todo with forbidden symbols (#,$,%)
