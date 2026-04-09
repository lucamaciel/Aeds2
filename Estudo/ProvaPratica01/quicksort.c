void swap(int *a, int *b) {
    int tmp = *a;
    *a = *b;
    *b = tmp;
}

int particionar(int *v, int esq, int dir) {
    int pivo = v[dir];
    int i = esq - 1;

    for (int j = esq; j < dir; j++) {
        if (v[j] <= pivo) {
            i++;
            swap(&v[i], &v[j]);
        }
    }
    swap(&v[i + 1], &v[dir]);
    return i + 1;
}

void quickSort(int *v, int esq, int dir) {
    if (esq < dir) {
        int pos = particionar(v, esq, dir);
        quickSort(v, esq, pos - 1);
        quickSort(v, pos + 1, dir);
    }
}