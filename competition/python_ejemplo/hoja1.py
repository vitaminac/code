# Escribe un programa que muestre por pantalla la cadena ¡Hola Mundo!
def ejercicio2():
    print("¡Hola Mundo!")


# Escribir un programa que almacene la cadena ¡Hola Mundo! en una variable
# y luego muestre por pantalla el contenido de la variable.
def ejercicio3():
    frase = "¡Hola Mundo!"
    print(frase)


# Escribir un programa que pregunte el nombre del usuario en la consola
# y después de que el usuario lo introduzca muestre por pantalla
# la cadena ¡Hola <nombre>!,
# donde <nombre> es el nombre que el usuario haya introducido.
def ejercicio4():
    nombre = input("Introduzca su nombre")
    print("Hola" + nombre)


# Escribir un programa que pregunte el nombre del usuario en la consola
# y después de que el usuario lo introduzca muestre
# por pantalla <NOMBRE> tiene <n> letras,
# donde <NOMBRE> es el nombre de usuario en mayúsculas
# y <n> es el número de letras que tienen el nombre.
def ejercicio5():
    nombre = input("Introduzca su nombre")
    print(nombre + " tiene " + str(len(nombre)) + " letras.")


# Escribir un programa que pida al usuario
# su peso (en kg) y estatura (en metros),
# calcule el índice de masa corporal y lo almacene en una variable,
# y muestre por pantalla la frase Tu índice de masa corporal es <imc>
# donde <imc> es el índice de masa corporal
# calculado redondeado con dos decimales
def ejercicio6():
    peso = int(input("Introduzca tu peso"))
    estatura = int(input("Introduca tu estatura"))
    print("Tu indice de masa corporal es " + str(peso / estatura**2))


# Escribir un programa que pida al usuario dos números enteros
# y muestre por pantalla la <n> entre <m> da un cociente <c>
# y un resto <r> donde <n> y <m> son los números introducidos
# por el usuario, y <c> y <r> son el cociente
# y el resto de la división entera respectivamente
def ejercicio7():
    n = int(input("Introduzca numero n: "))
    m = int(input("Introduzca numero m: "))
    cociente, resto = divmod(n, m)
    print(str(n)+" entre " + str(m) + " da un cociente " +
          str(cociente)+" y un resto " + str(resto))


# Escribir un programa que almacene la cadena de caracteres contraseña en una variable,
# pregunte al usuario por la contraseña e imprima por pantalla
# si la contraseña introducida por el usuario coincide
# con la guardada en la variable sin tener en cuenta mayúsculas y minúsculas.
def ejercicio8():
    contraseña = input("Introduzca la contraseña: ")
    if input("Confirma la contraseña: ").lower() == contraseña:
        print("correcto")
    else:
        print("incorrector")


if __name__ == "__main__":
    ejercicio7()
