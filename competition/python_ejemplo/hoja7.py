

# Escribir una función que pida un número entero entre 1 y 10
# y guarde en un fichero con el nombre tabla-n.txt
# la tabla de multiplicar de ese número,
# donde n es el número introducido.
def ejercicio1(n):
    with open("tabla-" + str(n)+".txt", "w") as f:
        for i in range(1, 10):
            f.write(str(i) + " * " + str(n) + str(i * n))


# Utilizando el ejercicio anterior crea la tabla de multiplicar del 1 al 10
# en distintos archivos y, a continuación,
# escribe una función que pida un número entero entre 1 y 10,
# lea el fichero tabla-n.txt con la tabla de multiplicar de ese número,
# donde n es el número introducido,
# y la muestre por pantalla. Si el fichero no existe debe
# mostrar un mensaje por pantalla informando de ello.
def ejercicio2():
    for i in range(n):
        ejercicio1(i)
    n = int(input("Introduzca el number n"))
    with open("tabla-" + str(n)+".txt", "w") as f:
        print(f.read())


# Crea un programa que lea los datos de un fichero de texto,
# con el mismo formato que el que se muestra más abajo,
# que transforme cada fila en un diccionario
# y lo añada a una lista llamada personas.
# Luego recorre las personas de la lista
# y para cada persona visualiza los datos
# de cada persona en un formato aceptable.
def ejercicio3():
    all = []
    with open("example.txt") as f:
        line = f.readline()
        data = line.split(";")
        all.append({"id": data[0], "name": data[1],
                    "surname": data[2], "data": data[3]})

    for data in all:
        print(all[data])


# Crea un programa que implemente un contador de visitas
# y lo almacene en un archivo llamado contador.txt.
# Si el fichero no existe o está vacío debe crearse insertar en él el número 0.
# Si existe, simplemente debe leerse el valor del contador
# y mostrarse el valor actual del mismo.
# A continuación, muestra un pequeño menú
# que permita incrementar o decrementar el contador
# según la opción elegida.
# Cada vez que se incremente o decremente debe mostrarse por
# pantalla y además escribirse en disco.
def ejercicio4():
    with open("contador.txt", "r+") as f:
        f.seek(0)
        lines = f.readlines()
        if lines:
            print(lines)
            last = int(lines[-1])
            choice = input("i) increment d) decrement : ")
            if choice == 'i':
                f.write(str(last + 1) + '\n')
            else:
                f.write(str(last - 1) + '\n')
        else:
            f.write(str(0) + '\n')


# Escribir un programa para gestionar un listín telefónico con los nombres
# y los teléfonos de los clientes de una empresa.
# El programa incorporar funciones crear el fichero con el listín si no existe,
# para consultar el teléfono de un cliente,
# añadir el teléfono de un nuevo cliente
# y eliminar el teléfono de un cliente.
# El listín debe estar guardado en el fichero de texto listin.txt
# donde el nombre del cliente y su teléfono deben aparecer
# separados por comas y cada cliente en una línea distinta.
class ejercicio5:
    def __init__(self):
        super().__init__()

    def consultar(self, nombre):
        with open("lilstin.txt", "r+") as f:
            for line in f:
                if line.startsWith(nombre + ","):
                    return line.split()[-1]

    def anadir(self, nombre, telefono):
        with open("listin.txt", "a") as f:
            f.write(nombre + "," + telefono)

    def eliminar(self, nombre):
        with open("lilstin.txt", "r+") as f:
            lines = f.readlines()
            filter(lambda x: x.startsWith(nombre + ","), lines)
            f.seek(0)
            f.writelines(lines)


if __name__ == "__main__":
    ej = ejercicio5()
