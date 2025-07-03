module.exports = {
  testEnvironment: 'jsdom',
  transform: { '^.+\\.[jt]sx?$': 'babel-jest' },
  moduleNameMapper: { '\\.(css|scss|sass)$': 'identity-obj-proxy' },
    setupFilesAfterEnv: ['./jest.setup.cjs'], 
};
