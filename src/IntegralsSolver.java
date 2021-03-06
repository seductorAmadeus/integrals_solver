public class IntegralsSolver {

    public static OutputData integrate(InputData inputData) {
        double a = inputData.getLowerLimit(), b = inputData.getUpperLimit();
        double precision = inputData.getPrecision();
        int iterationsCounter = 1;
        double temp;
        double h, iterationResult = 0, sum, previousIterationResult, observationalError;
        boolean limitsSwapped = false;
        if (a > b) {
            temp = a;
            a = b;
            b = temp;
            limitsSwapped = true;
        }
        h = b - a;
        do {
            previousIterationResult = iterationResult;
            sum = 0;
            for (int i = 1; i < iterationsCounter + 1; i++) {
                sum += getResultOfFunction(inputData, a + i * h);
            }
            iterationResult = h * ((getResultOfFunction(inputData, a) + getResultOfFunction(inputData, b)) * 0.5 + sum);
            observationalError = Math.abs(iterationResult - previousIterationResult) / 3;
            if (observationalError > precision) {
                h *= 0.5;
                iterationsCounter *= 2;
            }
        }
        while (observationalError > precision && iterationsCounter < Math.pow(2, 25)); // 2^25 - optimal number of iterations
        iterationResult *= limitsSwapped ? -1 : 1;
        return new OutputData(iterationResult, iterationsCounter, observationalError);
    }

    private static double getResultOfFunction(InputData inputData, double x) {
        double result = 0;
        switch (inputData.getFunction()) {
            case function1:
                result = Math.pow(Math.E, Math.pow(-x, 2)) - Math.cos(7 * x + Math.PI / 2);
                break;
            case function2:
                result = Math.pow(Math.pow(Math.E, x), 2);
                break;
            case function3:
                result = 8 + 2 * x - x * x;
                break;
        }
        return result;
    }
}
