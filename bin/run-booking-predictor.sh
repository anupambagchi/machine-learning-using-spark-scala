#!/usr/bin/env bash

#processcount=`ps auxww | grep java | grep -v "/bin/sh -c" | grep "LOOCVExample" | wc -l`
#if [ "$processcount" != "0" ] ; then
#      exit
#fi

CLASSNAME=com.anupambagchi.vacationrental.booking.CountingApp

pushd `dirname $0` > /dev/null
SCRIPTPATH=`pwd -P`
popd > /dev/null

JVM="-Xmn100M -Xms1024M -Xmx9048M"
source $SCRIPTPATH/setclasspath.sh
UBERJAR=${SCRIPTPATH}/../target/scala-2.11/machine-learning-using-spark-assembly-1.0.0.jar

echo Running Booking predictor using SPARK ...
echo spark-submit --master local[*] --class $CLASSNAME $UBERJAR $1 $2 $3
spark-submit --master local[*] --class $CLASSNAME $UBERJAR $1 $2 $3
