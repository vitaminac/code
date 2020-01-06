from math import pi, sqrt

# Escribir una función que muestre por pantalla
# el saludo ¡Hola amiga! cada vez que se la invoque.


def ejercicio1():
    print("¡Hola Mundo!")


# Escribir una función a la que se le pase una cadena <nombre>
# y muestre por pantalla el saludo ¡hola <nombre>!.
def ejercicio2(nombre):
    print("¡Hola {0}!".format(nombre))


# Escribir una función que reciba un número entero positivo
# y devuelva su factorial.
def ejercicio3(n):
    if n == 0:
        return 0
    return n * ejercicio3(n-1)


# Escribir una función que calcule el total de una factura tras aplicarle el IVA.
# La función debe recibir la cantidad sin IVA
# y el porcentaje de IVA a aplicar, y devolver el total de la factura.
# Si se invoca la función sin pasarle el porcentaje de IVA, deberá aplicar un 21%.
def ejercicio4(cantidad, iva):
    return cantidad * (1+iva)


# Escribir una función que calcule el área de un círculo
# y otra que calcule el volumen de un cilindro usando la primera función.
def ejercicio5(radius, h):
    return (pi*radius*radius, pi*radius*radius*h)


# Escribir una función que reciba una muestra de números en una lista
# y devuelva su media.
def ejercicio6(l):
    total = sum(l)
    return total / len(l)


# Escribir una función que reciba una muestra de números en una lista
# y devuelva otra lista con sus cuadrados.
def ejercicio7(l):
    return list(map(lambda x: x*x, l))


# Escribir una función que reciba una muestra de números en una lista
# y devuelva un diccionario con su media, varianza y desviación típica.
def ejercicio8(l):
    media = sum(l) / len(l)
    varianza = sum(map(lambda x: (x - media)**2, l))
    std = sqrt(varianza)
    return {
        "media": media,
        "varianza": varianza,
        "std": std
    }


# Escribir una función que calcule el máximo común divisor de dos números
# y otra que calcule el mínimo común múltiplo.
def ejercicio9(n, m):
    mcd = 0
    multiplo = n * m
    while n > 1 and m > 1:
        if n > m:
            n = n % m
        else:
            m = m % n
    if n > 1:
        mcd = n
    else:
        mcd = m

    return (mcd, multiplo // mcd)


# Escribir una que convierta un número decimal en binario
# y otra que convierta un número binario en decimal.
def ejercicio10(to_bin, to_dec):
    bin = ""
    while to_bin != 0:
        bin = str(to_bin % 2) + bin
        to_bin //= 2
    dec = 0
    for idx, i in enumerate(reversed(to_dec)):
        dec += int(i)*2**idx
    return (bin, dec)


# Escribir un programa que reciba una cadena de caracteres
# y devuelva un diccionario con cada palabra que contiene
# y su frecuencia. Escribir otra función que reciba el diccionario generado
# con la función anterior y devuelva una tupla con la palabra más repetida
# y su frecuencia.
def ejercicio10(palabra):
    d = dict.fromkeys(set(list(palabra)), 0)
    for key in d:
        d[key] = palabra.count(key)
    return d


if __name__ == "__main__":
    print(ejercicio10("asdasdgasgdfasdfasdfa"))
