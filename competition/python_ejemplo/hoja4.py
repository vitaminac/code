

# Escribir un programa que almacene las asignaturas de un curso
#  (por ejemplo Matemáticas, Física, Química, Historia y Lengua)
# en una lista y la muestre por pantalla.
def ejercicio1():
    curso = ["Matemáticas", "Física", "Química", "Historia", "Lengua"]
    for asignatura in curso:
        print(asignatura)


# Escribir un programa que almacene las asignaturas de un curso
# (por ejemplo Matemáticas, Física, Química, Historia y Lengua)
# en una lista y la muestre por pantalla el mensaje
# Yo estudio <asignatura>, donde <asignatura>
# es cada una de las asignaturas de la lista..
def ejercicio2():
    curso = ["Matemáticas", "Física", "Química", "Historia", "Lengua"]
    for asignatura in curso:
        print("Yo estudio " + asignatura)


# Escribir un programa que almacene las asignaturas de un curso
# (por ejemplo Matemáticas, Física, Química, Historia y Lengua) en una lista,
# pregunte al usuario la nota que ha sacado en cada asignatura,
# y después las muestre por pantalla con el mensaje
# En <asignatura> has sacado <nota> donde <asignatura>
# es cada una des las asignaturas de la lista
# y <nota> cada una de las correspondientes notas introducidas por el usuario.
def ejercicio3():
    curso = ["Matemáticas", "Física", "Química", "Historia", "Lengua"]
    notas = {}
    for asignatura in curso:
        notas[asignatura] = input(
            "Introduzca la nota que has sacado en " + asignatura + ": ")
    for asignatura in notas:
        print(asignatura + " : " + notas[asignatura])


# Escribir un programa que pregunte al usuario
# los números ganadores de la lotería primitiva,
# los almacene en una lista
# y los muestre por pantalla ordenados de menor a mayor.
def ejercicio4():
    ganadores = []
    ganador = input("Introduzca el nombre de ganador: ")
    while ganador:
        ganadores.append(ganador)
        ganador = input("Introduzca el nombre de ganador: ")
    print(sorted(ganadores))


# Escribir un programa que almacene en una lista los números del 1 al 10
# y los muestre por pantalla en orden inverso separados por comas
def ejercicio5():
    print(",".join(map(str, reversed(range(1, 11)))))


# Escribir un programa que pida al usuario una palabra
# y muestre por pantalla el número de veces que contiene cada vocal.
def ejercicio6():
    palabra = input("Introduzca un palabra: ")
    print("a: ", palabra.count("a"))
    print("e: ", palabra.count("c"))
    print("i: ", palabra.count("i"))
    print("o: ", palabra.count("o"))
    print("u: ", palabra.count("u"))


# Escribir un programa que almacene el abecedario en una lista,
# elimine de la lista las letras que ocupen posiciones múltiplos de 3,
# y muestre por pantalla la lista resultante.
def ejercicio7():
    abecedario = [chr(ord('a') + i) for i in range(26)]
    print(abecedario)
    print(list(filter(lambda letter: (ord(letter) - ord('a') + 1) % 3 != 0, abecedario)))


if __name__ == "__main__":
    ejercicio7()
