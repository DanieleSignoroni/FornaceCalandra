#! /bin/sh 
# ----------------------------------------------------- 
# -- Schedulatore lavori AC-- 
# ----------------------------------------------------- 
# -Java memory $1 
# -Classe $2 
# -Function $3 
# -Parameters $4 

cd  /opt/panthera/$DB2INSTANCE/PantheraAdmin/bin/
. ./SetVariables.sh
. ./SetEnv.sh PRIMROSE
export PRIMROSE_SRV=$PrimroseRoot610
. ./SetEnvPrimrose.sh
export CLASSPATH=$PATH_JAR:$CLASSPATH
$JavaRoot/bin/java $1 $2 $3 $4 
