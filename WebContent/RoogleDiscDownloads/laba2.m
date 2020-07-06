clc
close all
f0 = 2400;
teta = [-pi/4, pi/4, pi/4, -pi/4];
Vm = 600;
Vi = 1200;
T = 1/Vm;
q = 4;
A = 1;
n = 20;
dF = f0/n;
F = -13000:dF:13000;
S = zeros(q, length(F));
i1 = [0, 0, 1, 1];
i2 = [0, 1, 0, 1];
%Амплитудные спектры
count = 1
for i = 1:q
    si1 = A*(1-(2*i1(i)/(sqrt(q)-1)));
    si2 = A*(1-(2*i2(i)/(sqrt(q)-1)));
    S(i,:) = si1*sqrt(T/2)*(sinc((F-f0)*T) + sinc((F+f0)*T)).*exp(1j*pi*F*T)+ si2/1j*sqrt(T/2)*(sinc((F-f0)*T) - sinc((F+f0)*T)).*exp(1j*pi*F*T)
    subplot(2,2, count);
    count = count + 1;
    plot(F(length(F)/2:length(F)), abs(S(i, length(F)/2:length(F))));
    hold on;
    xlabel('f');
    ylabel('s(f)');
    grid on;
    disp(abs(S(1)));
end

%Спектр последовательности сигналов
N = 10;
m = round((q-1)*rand(1,N));
disp(m);
Si = zeros(1, length(F));
figure
plot(F(length(F)/2:length(F)), abs(S(i, length(F)/2:length(F))));
hold on;
grid on;
for L = 0:N-1
    Si = Si + S(m(L+1) + 1, :).*exp(-(1j*2*pi*(L)*T)*F);
end
plot(F(length(F)/2:length(F)), abs(Si(length(F)/2:length(F))), 'm');

figure
plot(F(length(F)/2:length(F)), abs(S(i, length(F)/2:length(F))));
hold on;
grid on;
N = 5;
mI = [1 1 1 1 1];
m = round((q-1)*rand(1,N));
disp(m);
Si = zeros(1, length(F));
for L = 0:N-1
    Si = Si + S(mI(L+1) + 1, :).*exp(-(1j*2*pi*(L)*T)*F);
end
plot(F(length(F)/2:length(F)), abs(Si(length(F)/2:length(F))), 'm');






