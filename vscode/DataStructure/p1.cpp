#include <bits/stdc++.h>
using namespace std;

struct Node{
    int c;
    Node *nxt;
};

int Count(Node* p) {
    if (p->nxt == nullptr) return 1;
    else return Count(p->nxt) + 1;
}

int main()
{
    Node *st = new Node(), *now = st;
    int t;
    cin >> t;
    st->c = t;
    st->nxt = nullptr;
    while (cin >> t) {
        Node *h = new Node();
        h->c = t;
        h->nxt = nullptr;
        now->nxt = h;
        now = h;
    }
    cout << Count(st) << '\n';
    system("pause");
}