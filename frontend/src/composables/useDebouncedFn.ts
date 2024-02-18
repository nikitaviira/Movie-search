export function useDebouncedFn<T extends(...args: any[]) => void>(fn: T, delay: number): T {
  let timeoutId: number | undefined;

  return function(this: any, ...args: any[]) {
    clearTimeout(timeoutId);

    timeoutId = setTimeout(() => {
      fn.apply(this, args);
    }, delay);
  } as T;
}
