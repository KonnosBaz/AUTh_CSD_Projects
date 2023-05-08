#include <stdio.h>
#include <stdlib.h>
#include <malloc.h>
#include <string.h>

int metra_grames(FILE* x)
{
    int count=1;
    char c='a';
    while(c!=EOF)
    {
        c=fgetc(x);
        if (c=='\n')
        {
            count++;
        }
    }
    return count;
}

void pinakas1(FILE* x,int N,int array[])
{
    for(int i=0;i<N;i++)
    {
        fscanf(x,"%d ",&array[i]);
    }
}

void metric0(int uns[],int f[],int all[],int unC,int alC)
{
    for(int i=0;i<unC;i++)
    {
        int count=0;
        for(int j=0;j<alC;j++)
        {
            if(uns[i]==all[j])
                count++;
        }
        f[i]=count;
    }
}

void metric1(int uns[],int d[],int all[],int unC,int alC)
{
    for(int i=0;i<unC;i++)
    {
        int count=0;
        for(int j=0;j<alC;j++)
        {
            if(all[j]!=uns[i])
                count++;
            else
                break;
        }
        if (count==70)
            d[i]=10;
        else
            d[i]=(count)/7;
    }
}

void metric2(int f[],int d[],int fkaid[],int unC)
{
    for(int i=0;i<unC;i++)
    {
        fkaid[i]=f[i]+d[i];
    }
}

void metric3(int d[],int f[],int K,float rd[],int unC)
{
    for(int i=0;i<unC;i++)
    {
        if(f[i]!=0)
            rd[i]=(float)d[i]-((float)K/(float)f[i]);
        else
            rd[i]=0;
    }
}

void double_sort_float(float base[],int dragged[],int size)
{
    float temp;
    for(int i=0;i<size-1;i++)
    {
        for(int j=0;j<size-1-i;j++)
        {
            if (base[j]>base[j+1])
            {
                temp = base[j];
                base[j]=base[j+1];
                base[j+1]=temp;
                temp = dragged[j];
                dragged[j]=dragged[j+1];
                dragged[j+1]=(int)temp;
            }
            if (base[j]==base[j+1])
            {
                if(dragged[j]<dragged[j+1])
                {
                temp = base[j];
                base[j]=base[j+1];
                base[j+1]=temp;
                temp = dragged[j];
                dragged[j]=dragged[j+1];
                dragged[j+1]=(int)temp;
                }
            }
        }
    }
}

void double_sort(int base[],int dragged[],int size)
{
    int temp;
    for(int i=0;i<size-1;i++)
    {
        for(int j=0;j<size-1-i;j++)
        {
            if (base[j]>base[j+1])
            {
                temp = base[j];
                base[j]=base[j+1];
                base[j+1]=temp;
                temp = dragged[j];
                dragged[j]=dragged[j+1];
                dragged[j+1]=(int)temp;
            }
            if (base[j]==base[j+1])
            {
                if(dragged[j]<dragged[j+1])
                {
                temp = base[j];
                base[j]=base[j+1];
                base[j+1]=temp;
                temp = dragged[j];
                dragged[j]=dragged[j+1];
                dragged[j+1]=(int)temp;
                }
            }
        }
    }
}

void ektypwse_megalyterous(int printer[],int M,int N)
{
    for(int i=N-1;i>=N-M;i--)
    {
        printf("%d ",printer[i]);
    }
}


int main()
{
    //dhlwseis metavlhtwn
    char filename[30];
    int K,A,B;
    FILE* fp;
    //=======================

    //diavasma...
    scanf("%d %d",&A,&B);
    if(A<1||A>49)
    {
        printf("Wrong Input!");
        exit(1);
    }
    if(B<0||B>3)
    {
        printf("Wrong Input!");
        exit(1);
    }
    //strcpy(filename,"input.txt");
    scanf("%s",filename);
    fp=fopen(filename,"r");
    if (fp==NULL)
    {
        printf("File Error!");
        exit(2);
    }
    fclose(fp);

     //==============================

    fp=fopen(filename,"r");
    K=metra_grames(fp)*7;
    fclose(fp);

    //==========
    int allNums[K];
    //==========

    fp=fopen(filename,"r");
    pinakas1(fp,K,allNums);
    fclose(fp);

    //==========
    int noumera[49];
    for(int i=0;i<49;i++)
    {
        noumera[i]=i+1;
    }
    //==========

    //==========
    int f[49];
    int d[49];
    int fkaid[49];
    float rd[49];
    //==========

    metric0(noumera,f,allNums,49,K);
    metric1(noumera,d,allNums,49,K);
    metric2(f,d,fkaid,49);
    metric3(d,f,K/7,rd,49);

    if(B==0)
    {
        double_sort(f,noumera,49);
    }
    else if(B==1)
    {
        double_sort(d,noumera,49);
    }
    else if(B==2)
    {
        double_sort(fkaid,noumera,49);
    }
    else if(B==3)
    {
        double_sort_float(rd,noumera,49);
    }

    ektypwse_megalyterous(noumera,A,49);

}

