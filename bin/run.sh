for i in $(seq 1 10000)
do
echo -e "\nRun n.o " $i
java -cp .:/home/rlm/Desktop/genclass.jar main.TheRestaurant <inData
done
