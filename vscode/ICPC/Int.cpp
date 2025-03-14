#include <vector>
using namespace std;
using ll = long long;
const int base = 1e9;

struct Int{
    vector <ll> num;
    Int() : num() {}
    Int(ll x){
        while(x) {
            num.push_back(x % base);
            x /= base;
        }
    }
    int size() {return num.size();}
    void resize(int x) {num.resize(x);}
    ll& operator [](int x) {return num[x];}
    ll back() {return num.back();}
    void push_back(ll x) {num.push_back(x);}
    void pop_back() {num.pop_back();}
    void print() {
        for (int i = num.size() - 1; ~i; i --) {
            if (i == num.size() - 1) cout << num[i];
            else {
                int t = 10, cnt = 0;
                while(num[i] * t < base && cnt < 9) {
                    t *= 10;
                    cnt ++;
                }
                for (int i = 1; i <= cnt; i ++) cout << '0';
                cout << num[i];
            }
        }
    }
    friend Int operator + (Int a, Int b) {
        if (a.size() < b.size()) swap(a, b);
        Int res;
        ll t = 0;
        for (int i = 0; i < a.size(); i ++) {
            t += a[i];
            if (i < b.size()) t += b[i];
            res.push_back(t % base);
            t /= base;
        }
        if (t) res.push_back(t);
        return res;
    }
    friend Int operator -(Int a, Int b) {
        Int res;
        ll t = 0;
        int i;
        for (i = 0; i < a.size(); i ++) {
            t += a[i];
            if (i < b.size()) t -= b[i];
            if (t < 0) t += base, a[i + 1] --;
            res.push_back(t % base);
            t /= base;
        }
        for (i; i < b.size(); i ++) {
            t -= b[i];
            res.push_back(t % base);
            t /= base;
        }
        while (res.size() > 1 && !res.back()) res.pop_back();
        return res;
    }
    friend Int operator *(Int a, Int b) {
        Int res;
        res.resize(a.size() + b.size());
        for (int i = 0; i < a.size(); i ++) 
            for (int j = 0; j < b.size(); j ++)
                res[i + j] += a[i] * b[j];
        for (int i = 0; i < res.size(); i ++) 
            if (res[i] > base) res[i + 1] += res[i] / base, res[i] %= base;
        while (res.size() > 1 && !res.back()) res.pop_back();
        return res;
    }
    friend Int operator ^(Int a, int b) {
        Int res(1);
        while (b) {
            if (b & 1) res = res * a;
            a = a * a;
            b >>= 1;
        }
        return res;
    }
};