for i in $(seq 1 10000)
do
echo -e "\nRun n.o " $i
java -cp .:/home/raquel/Desktop/SD/Pratica/genclass.jar main.TheRestaurant <inData
done
