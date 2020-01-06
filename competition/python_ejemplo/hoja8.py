

# Localiza el error en el siguiente bloque de código.
# Crea una excepción para evitar que el programa se bloquee
# y además explica en un mensaje al usuario
# la causa y/o solución
def ejercicio1():
    try:
        resultado = 10 / 0
    except ZeroDivisionError as e:
        raise Exception("Tonto")


# Localiza el error en el siguiente bloque de código.
# Crea una excepción para evitar que el programa se bloquee
# y además explica en un mensaje al usuario la causa y/o solución
def ejercicio2():
    lista = [1, 2, 3, 4, 5]
    try:
        lista[10]
    except IndexError as e:
        raise Exception("index error")


# Localiza el error en el siguiente bloque de código.
# Crea una excepción para evitar que el programa se bloquee
# y además explica en un mensaje al usuario la causa y/o solución:
def ejercicio3():
    colores = {'rojo': 'red', 'verde': 'green', 'negro': 'black'}
    try:
        colores['blanco']
    except KeyError:
        raise Exception("key error")


# Localiza el error en el siguiente bloque de código.
# Crea una excepción para evitar que el programa se bloquee
# y además explica en un mensaje al usuario la causa y/o solución.
def ejercicio4():
    try:
        resultado = 15 + "20"
    except TypeError as e:
        raise Exception("tipo erroneo")


# Realiza una función llamada agregar_una_vez(lista, el)
# que reciba una lista  y un elemento.
# La función debe añadir el elemento al final de la lista
# con la condición de no repetir ningún elemento.
# Además si este elemento ya se encuentra en la lista
# se debe invocar un error de tipo ValueError
# que debes capturar y mostrar este mensaje en su lugar:
def ejercicio5(e):
    elementos = [1, 5, -2]
    if e in elementos:
        raise Exception("Error: Imposible añadir elementos duplicados")
    else:
        elementos.append(e)
    print(elementos)


if __name__ == "__main__":
    ejercicio5(5)
