

# Escribir un programa que pregunte al usuario su edad
# y muestre por pantalla si es mayor de edad o no.
def ejercicio1():
    edad = int(input("Introduzca su edad"))
    if edad >= 18:
        print("su edad es mayor")
    else:
        print("su edad es menor")


# Escribir un programa que almacene la cadena de caracteres contraseña
# en una variable,
# pregunte al usuario por la contraseña e imprima por pantalla si la
# contraseña introducida por el usuario coincide
# con la guardada en la variable sin
# tener en cuenta mayúsculas y minúsculas.
def ejercicio2():
    contraseña = "1234AbCd"
    if input("Confirma la contraseña: ") == contraseña:
        print("correcto")
    else:
        print("incorrector")


# Escribir un programa que pida al usuario dos números
# y muestre por pantalla su división.
# Si el divisor es cero el programa debe mostrar un error.
def ejercicio3():
    n = int(input("Introduzca un numero"))
    divisor = int(input("Introduzca un divisor"))
    if divisor == 0:
        print("divisor es cero")
    else:
        print("resultado es " + str(n / divisor))


# Para tributar un determinado impuesto se debe ser mayor de 16 años
# y tener unos ingresos iguales o superiores a 1000 € mensuales.
# Escribir un programa que pregunte al usuario su edad
# y sus ingresos mensuales y muestre por pantalla
# si el usuario tiene que tributar o no.
def ejercicio4():
    edad = int(input("Introduzca su edad"))
    ingreso = int(input("Introduzca su ingreso"))
    if edad >= 16 and ingreso >= 1000:
        print("Tienes que tributar")


# Los alumnos de un curso se han dividido
# en dos grupos A y B de acuerdo al sexo y el nombre.
# El grupo A esta formado por las mujeres
# con un nombre anterior a la M
# y los hombres con un nombre posterior a la N
# y el grupo B por el resto.
# Escribir un programa que pregunte al usuario su nombre y sexo,
# y muestre por pantalla el grupo que le corresponde.
def ejercicio5():
    personas = [
        {"sexo": "F", "nombre": "Maria"},
        {"sexo": "F", "nombre": "Ana"},
        {"sexo": "F", "nombre": "Victoria"},
        {"sexo": "M", "nombre": "Juan"},
        {"sexo": "M", "nombre": "Victor"},
        {"sexo": "M", "nombre": "Rodrigo"},
    ]
    grupoA = list(filter(lambda p: (
        p['sexo'] == 'F' and p['nombre'][0] <= 'M')or (p['sexo'] == 'M' and p['nombre'][0] >= 'N'), personas))
    grupoB = list(filter(lambda p: p not in grupoA, personas))
    print(grupoA, grupoB)


# Escribir un programa que pregunte al usuario su renta anual
# y muestre por pantalla el tipo impositivo que le corresponde.
def ejercicio6():
    renta = int(input("Introduzca tu renta anual"))
    if renta < 10000:
        print("5%")
    elif renta < 20000:
        print("15%")
    elif renta < 35000:
        print("20")
    elif renta < 60000:
        print("30%")
    else:
        print("45%")


# Escribir un programa que pida al usuario su peso (en kg) y estatura (en metros),
# calcule el índice de masa corporal y lo almacene en una variable,
# y muestre por pantalla la frase
# Tu índice de masa corporal es <imc> donde <imc>
# es el índice de masa corporal calculado redondeado con dos decimales
def ejercicio7():
    peso = int(input("Introduzca tu peso"))
    estatura = int(input("Introduzca tu estatura"))
    indice = peso / estatura ** 2
    print("Tu índice de masa corporal es " + str(round(indice, 2)))


# Escribir un programa que pregunte al usuario una cantidad a invertir,
# el interés anual y el número de años,
# y muestre por pantalla el capital obtenido en la inversión.
def ejercicio9():
    cantidad = int(input("Introduzca la cantidad a invertir"))
    interes = float(input("Introduzca el interes anual"))
    año = int(input("Introduzca el numero de año"))
    print(str(cantidad * interes ** año))


if __name__ == "__main__":
    ejercicio5()
