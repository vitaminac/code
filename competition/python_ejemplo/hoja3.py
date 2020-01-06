

# Escribir un programa que pida al usuario una palabra
# y la muestre por pantalla 10 veces.
def ejercicio1():
    parabra = input("Introduzca una palabra")
    for i in range(10):
        print(parabra)


# Escribir un programa que pregunte al usuario su edad
# y muestre por pantalla todos los años que ha cumplido
def ejercicio2():
    edad = int(input("Introduzca tu edad"))
    for i in range(edad):
        print("cumple " + str(i))


# Escribir un programa que pida al usuario un número entero positivo
# y muestre por pantalla todos los números impares
# desde 1 hasta ese número separados por comas
def ejercicio3():
    for i in range(1, int(input("Introduzca un numero: "))+1, 2):
        print(i)


# Escribir un programa que pida al usuario un número entero positivo
# y muestre por pantalla la cuenta atrás
# desde ese número hasta cero separados por comas
def ejercicio4():
    for i in reversed(range(int(input("Introduzca un numero: ")) + 1)):
        print(i)


# Escribir un programa que pregunte al usuario una cantidad a invertir,
# el interés anual y el número de años,
# y muestre por pantalla el capital
# obtenido en la inversión cada año que dura la inversión
def ejercicio5():
    cantidad = int(input("Introduzca la cantidad a invertir"))
    interes = float(input("Introduzca el interes anual"))
    año = int(input("Introduzca el numero de año"))
    print(str(cantidad * interes ** año))


# Escribir un programa que pida al usuario un número entero
# y muestre por pantalla un triángulo rectángulo como el de más abajo,
# de altura el número introducido.
def ejercicio6():
    n = int(input("Introduzca un numero n: "))
    for i in range(n+1):
        print("*" * i)


# Escribir un programa que muestre por pantalla
# la tabla de multiplicar del 1 al 10
def ejercicio7():
    for i in range(1, 10):
        for j in range(1, 10):
            print(str(i) + " * " + str(j) + " = " + str(i*j))


# Escribir un programa que pida al usuario un número entero
# y muestre por pantalla un triángulo rectángulo como el de más abajo.
def ejercicio8():
    for i in range(1, int(input("Introduzca el numero n: ")) + 1, 2):
        for j in reversed(range(1, i, 2)):
            print(j,  end=' ')
        print()


# Escribir un programa que almacene la cadena de caracteres contraseña
# en una variable, pregunte al usuario por la contraseña
# hasta que introduzca la contraseña correcta.
def ejercicio9():
    contraseña = "1234AbCd"
    while input("Confirma la contraseña: ") != contraseña:
        continue


# Escribir un programa que pida al usuario un número entero
# y muestre por pantalla si es un número primo o no
def ejercicio10():
    n = int(input("Introduzca un numero: "))
    for i in range(2, n):
        if n % i == 0:
            print("No es primo")
            return
    print("Es primo")


if __name__ == "__main__":
    ejercicio9()
